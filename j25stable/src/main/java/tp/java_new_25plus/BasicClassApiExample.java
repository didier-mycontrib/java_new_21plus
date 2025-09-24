package tp.java_new_25plus;

/*
Class-Api (stable in  java 25) in a official api to generate byte-code at runtime (it can replace ASM)
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.classfile.ClassFile;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.nio.file.Path;

import static java.lang.classfile.ClassFile.ACC_PUBLIC;
import static java.lang.classfile.ClassFile.ACC_STATIC;

public class BasicClassApiExample {

    /* cet exemple de code génère une classe HelloWorld avec une méthode main qui affiche "Hello World" dans la console:

    public class HelloWorld {
        public static void main(String[] args) {
            System.out.println("Hello World");
        }
      }

     */


    public static void generateHelloWorldClass()  {
        try {
            System.out.println("générer dynamiquement le byte-cote de la classe HelloWorld via l'api Class-Api");
            ClassFile helloWorldClass = ClassFile.of();
            helloWorldClass.buildTo(Path.of("HelloWorld.class"), ClassDesc.of("HelloWorld"),
                    classBuilder ->classBuilder
                            .withMethodBody("main", MethodTypeDesc.ofDescriptor("([Ljava/lang/String;)V"), ACC_PUBLIC | ACC_STATIC,
                                    codeBuilder -> codeBuilder
                                            .getstatic(ClassDesc.of("java.lang.System"), "out", ClassDesc.of("java.io.PrintStream"))
                                            .ldc("Hello World")
                                            .invokevirtual(ClassDesc.of("java.io.PrintStream"), "println", MethodTypeDesc.ofDescriptor("(Ljava/lang/Object;)V"))
                                            .return_()
                            ));
            System.out.println("HelloWorld.class generated successfully.");
            //NB: le fichier HelloWorld.class est généré dans le répertoire courant du projet
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  void lancerProcessHelloWorldClass()  {
        try {
            System.out.println("lancer l'exécution de la classe HelloWorld générée dynamiquement:");
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("java", "HelloWorld");
            Process process = pb.start();
            BufferedReader outputReaderOfSubProcess = new BufferedReader(new
                    InputStreamReader(process.getInputStream()));
            System.out.println("output of HelloWorld sub process (first line) =" +
                    outputReaderOfSubProcess.readLine());
            System.out.println("pid of HelloWorld sub process=" + process.pid());
        } catch (Throwable e) {
            System.err.println("Error launching HelloWorld process:");
            e.printStackTrace();
        }
    }
}

/*
Autres possibilités de l'api Class-Api:

Transform class files

Read a jar file
Read each class file from the jar file
Optimise the bytecode of each class
Write the result to a new jar
 */