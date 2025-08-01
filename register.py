import os
import re

def find_classes_in_package(source_dir, package_name):
    """
    Scans a directory for Java files and extracts class names within a given package.
    """
    classes = []
    # Normalize package name to directory path
    package_path = package_name.replace('.', os.sep)
    target_dir = os.path.join(source_dir, package_path)

    if not os.path.isdir(target_dir):
        print(f"Warning: Directory not found: {target_dir}")
        return classes

    for root, _, files in os.walk(target_dir):
        for file in files:
            if file.endswith(".java"):
                filepath = os.path.join(root, file)
                relative_path = os.path.relpath(filepath, target_dir)
                class_name_base, _ = os.path.splitext(relative_path)

                # Handle inner classes or nested classes (though Kryo might need specific handling for these)
                # For simplicity, we'll focus on top-level classes in each directory for now.
                # If you have nested classes like `Outer.Inner`, this simple script might need adjustments.
                
                # Construct the fully qualified class name
                # Calculate the sub-package path relative to the target_dir
                sub_package_path = os.path.relpath(root, target_dir).replace(os.sep, '.')
                
                current_class_name = os.path.splitext(file)[0]
                
                if sub_package_path and sub_package_path != '.':
                    full_class_name = f"{package_name}.{sub_package_path}.{current_class_name}"
                else:
                    full_class_name = f"{package_name}.{current_class_name}"

                # Basic check for comments or non-class definitions that might be mistaken for classes
                # This is a very rudimentary check and might need refinement.
                with open(filepath, 'r', encoding='utf-8') as f:
                    content = f.read()
                    # Look for 'public class', 'class', 'public enum', 'enum'
                    if re.search(r'(public\s+)?(class|enum)\s+' + re.escape(current_class_name) + r'\b', content):
                        # Exclude anonymous inner classes or other complex scenarios if necessary
                        # For now, we assume simple class definitions
                        classes.append(full_class_name)
    
    # Remove duplicates and sort
    return sorted(list(set(classes)))

def generate_kryo_register_code(classes_to_register, base_package):
    """
    Generates the Python code snippet for Kryo registration.
    """
    code_lines = []
    code_lines.append("def register_models(kryo):")
    code_lines.append("    \"\"\"Registers all model classes for Kryo.\"\"\"")
    
    # Add imports that might be needed if running this python code to generate Java code
    # But for generating Java code, we just need the Kryo object passed in.
    
    standard_types = [
        "Integer", "String", "Void", "Boolean", "Byte", "Short", "Long", "Float", "Double", "Char",
        "List", "ArrayList", "HashMap", "ConcurrentMap", "ConcurrentHashMap", "Object", "Object[]"
    ]
    
    # Kryo often requires registering primitive types and common collections explicitly.
    # Let's ensure some of these are handled if not already present.
    
    # We will assume that the base package itself might contain classes to register
    # and also sub-packages.
    
    # Let's refine the class names to match Java's convention for kryo.register
    # e.g., "com.example.models.User" becomes "com.example.models.User.class"
    
    registered_classes_fully_qualified = set()

    # First, register the explicit ones from your example, assuming they are correct.
    explicitly_registered = {
        "MessageType", "Message", "HashMap", "ConcurrentMap", "ConcurrentHashMap",
        "Error", "ServerError", "UserError", "AuthError", "GameError", "MenuError",
        "Integer", "Gender", "User", "List", "ArrayList", "String", "Void", "Result",
        "Lobby", "ChatType", "Chat", "LobbyUser", "HeartBeatPacket", "Object", "Object[]",
        "Player"
    }
    
    # Add base package classes and sub-package classes
    for cls in classes_to_register:
        # We need to format this for Java: ClassName.class
        # Assuming cls is already fully qualified like "com.example.models.User"
        java_class_format = f"{cls}.class"
        
        # Avoid re-registering standard types if they are part of the model scan
        # (This is a simplification; Kryo might have specific ways to handle generics)
        simple_class_name = cls.split('.')[-1]
        if simple_class_name not in standard_types and cls not in explicitly_registered and cls not in registered_classes_fully_qualified:
             code_lines.append(f"    kryo.register({java_class_format});")
             registered_classes_fully_qualified.add(cls)

    # Add a placeholder for the base package itself if it contains classes directly
    # This part is tricky without knowing the exact structure.
    # For example, if 'models.MyModel' exists, it should be registered.
    # The find_classes_in_package should ideally return these.

    # Add standard Kryo registrations that are often needed.
    # These might be redundant if already present, but it's safer.
    standard_registrations = [
        "java.util.ArrayList", "java.util.HashMap", "java.util.concurrent.ConcurrentHashMap",
        "java.lang.Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Byte", 
        "java.lang.Short", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Object",
        "java.lang.Object[]", "java.lang.Void"
    ]
    
    # Add registrations for collections if they are used as fields in models
    collection_registrations = [
        "java.util.List", "java.util.Map", "java.util.Set", "java.util.Collection"
    ]

    # Add these common ones if they are not already in the generated list
    for reg in standard_registrations + collection_registrations:
        if reg not in registered_classes_fully_qualified and reg.startswith("java.lang.") or reg.startswith("java.util.") or reg.startswith("java.util.concurrent."):
            # Check if the simple name is already handled to avoid double registration
            simple_name = reg.split('.')[-1]
            already_added = False
            for line in code_lines:
                if f"{simple_name}.class" in line:
                    already_added = True
                    break
            if not already_added:
                 code_lines.append(f"    kryo.register({reg}.class);")
                 registered_classes_fully_qualified.add(reg) # Track that we've conceptually registered it

    # Add the explicitly registered classes from your example to ensure they are included
    for reg_class in explicitly_registered:
        # Need to find the fully qualified name. This script can't know where `Player` or `User` are defined without more context.
        # Assuming they are within the 'models' base package or its subpackages.
        
        # Let's try to infer common packages for these.
        potential_full_names = []
        if reg_class in ["Player", "Lobby", "LobbyUser", "Chat", "ChatType", "HeartBeatPacket"]:
            potential_full_names.append(f"{base_package}.network.{reg_class}")
            potential_full_names.append(f"{base_package}.game.{reg_class}")
        elif reg_class in ["User", "Gender"]:
            potential_full_names.append(f"{base_package}.user.{reg_class}")
        elif reg_class in ["Result", "Error", "ServerError", "UserError", "AuthError", "GameError", "MenuError"]:
            potential_full_names.append(f"{base_package}.result.{reg_class}")
            if reg_class.endswith("Error"):
                 potential_full_names.append(f"{base_package}.result.errorTypes.{reg_class}")
        elif reg_class in ["Message", "MessageType"]:
            potential_full_names.append(f"{base_package}.network.{reg_class}")
        elif reg_class in ["Integer", "String", "Void", "Object", "Object[]"]: # standard types handled above
             pass
        elif reg_class in ["List", "ArrayList", "HashMap", "ConcurrentMap", "ConcurrentHashMap"]: # collections handled above
             pass
        else:
            potential_full_names.append(f"{base_package}.{reg_class}")

        added_this_one = False
        for p_name in potential_full_names:
             if p_name not in registered_classes_fully_qualified:
                code_lines.append(f"    kryo.register({p_name}.class);")
                registered_classes_fully_qualified.add(p_name)
                added_this_one = True
                break # Assume first match is good enough for this example

    return "\n".join(code_lines)

# --- Configuration ---
# IMPORTANT: Set this to the root directory of your Java source code.
# For example, if your project structure is:
# my_java_project/
# ├── src/
# │   └── main/
# │       └── java/
# │           └── com/
# │               └── example/
# │                   └── models/
# │                       ├── game/
# │                       │   └── Player.java
# │                       ├── network/
# │                       │   └── Message.java
# │                       └── user/
# │                           └── User.java
#
# Then source_directory should be 'my_java_project/src/main/java'
source_directory = './shared/src/main/java'  # <<< --- MODIFY THIS PATH

# The base package for your models (e.g., "com.example.models")
base_package = 'models' # <<< --- MODIFY THIS TO YOUR BASE PACKAGE

# --- Main Execution ---
if __name__ == "__main__":
    print(f"Scanning directory: {os.path.abspath(source_directory)}")
    print(f"Assuming base package: {base_package}")

    # Find all classes within the base package and its subdirectories
    all_model_classes = find_classes_in_package(source_directory, base_package)

    if not all_model_classes:
        print("\nNo model classes found. Please check:")
        print(f"1. The 'source_directory' is correct: {os.path.abspath(source_directory)}")
        print(f"2. The 'base_package' is correct: {base_package}")
        print("3. Your Java project structure under the source directory.")
    else:
        print(f"\nFound {len(all_model_classes)} model classes:")
        for cls in all_model_classes:
            print(f"- {cls}")

        # Generate the Python code that will output the Java registration code
        # We pass the base_package so the generator knows where to assume classes are.
        generated_java_code_snippet = generate_kryo_register_code(all_model_classes, base_package)

        print("\n--- Generated Kryo Registration Code Snippet (for your Java NetworkRegister class) ---")
        print("```java")
        print(generated_java_code_snippet)
        print("```")
        print("\nCopy the code inside the ```java block and paste it into your NetworkRegister.java file.")
        print("Remember to also include any necessary imports at the top of your Java file!")
