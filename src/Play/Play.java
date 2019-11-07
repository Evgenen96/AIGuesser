package Play;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Play {

    public static void main(String[] args) throws Exception {
        TNode top = new TNode();
        TNode currentNode = top;
        Scanner sc = new Scanner(System.in, "Cp1251");
        TNode.reader = new FileReader("tree2.txt");
        currentNode = top.load();
        TNode.reader.close();
        int answer;
        top = currentNode;
        System.out.println(top.question);
        currentNode = currentNode.getAnswer(true);
        while (true) {
            if (currentNode.isLastQuestion()) {
                System.out.println("Это " + currentNode.question + "?");
                answer = sc.nextInt();
                if (answer == 1) {
                    System.out.println("Ура, я угадал");
                } else {
                    currentNode.asked = true;
                    if (!currentNode.parentNode.nodesQuestionAllAsked()) {
                        System.out.println("еще!");
                        continue;
                    }
                    System.out.println("Кто это тогда?");
                    String animal = sc.nextLine();
                    animal = sc.nextLine();
                    System.out.println("Напишите что отличает его от " + currentNode.question);
                    String animalDescription = sc.nextLine();
                    currentNode.parentNode.addNode(animalDescription);
                    currentNode.parentNode.nodes.get(currentNode.parentNode.nodes.size() - 1).addNode(animal);
                    TNode.writer = new FileWriter("tree2.txt", false);
                    top.save(top);
                    TNode.writer.close();
                }
                break;
            }
            if (currentNode.nodesQuestionAllAsked()) {
                System.out.println("вопросы кончились");
                System.out.println("Кто это?");
                String animal = sc.nextLine();
                animal = sc.nextLine();
                System.out.println("Как вы бы описали его");
                String animalDescription = sc.nextLine();
                currentNode.addNode(animalDescription);
                currentNode.nodes.get(currentNode.nodes.size() - 1).addNode(animal);
                TNode.writer = new FileWriter("tree2.txt", false);
                top.save(top);
                TNode.writer.close();
                break;
            }
            System.out.println(currentNode.question);
            answer = sc.nextInt();
            currentNode.asked = true;
            currentNode = currentNode.getAnswer(answer == 1);
        }
    }
}
