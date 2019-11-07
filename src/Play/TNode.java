package Play;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class TNode {

    public TNode parentNode;
    public static int counter = 0;
    public ArrayList<TNode> nodes;
    public String question;
    public boolean asked = false;
    public static FileWriter writer;
    public static FileReader reader;
    public static Random random = new Random();

    public TNode() {
        parentNode = null;
        question = "Загадайте животное";
        nodes = new ArrayList();
    }

    public TNode(TNode parent) {
        parentNode = parent;
        nodes = new ArrayList();
    }

    public TNode(TNode parent, String q) {
        parentNode = parent;
        nodes = new ArrayList();
        question = q;
    }

    public void addNode() {
        nodes.add(new TNode(this));
    }

    public void addNode(String q) {
        nodes.add(new TNode(this, q));
    }

    public void save(TNode top) throws IOException {
        if (top.parentNode != null) {
            writer.write(top.question + "\n");
        }
        ArrayDeque<TNode> queue = new ArrayDeque<>();
        queue.addAll(top.nodes);
        while (!queue.isEmpty()) {
            save(queue.pop());
        }
        writer.append("\n");
    }

    public TNode load() throws IOException {
        BufferedReader bReader = new BufferedReader(reader);
        String line;
        int level = 0;
        TNode currentNode = new TNode();
        TNode topNode = currentNode;
        while ((line = bReader.readLine()) != null) {
            if (!line.equals("")) {
                currentNode.addNode(line);
 //               System.out.println(++level + " " + line);
                currentNode = currentNode.nodes.get(currentNode.nodes.size() - 1);
            } else if (currentNode.parentNode != null) {
                currentNode = currentNode.parentNode;
                --level;
            }
        }
        return topNode;
    }

    public boolean isLastQuestion() {
        if (nodes.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean nodesQuestionAllAsked() {
        if (parentNode == null) {
            return false;
        }
        for (TNode node : nodes) {
            if (!node.asked) {
                return false;
            }
        }
        return true;
    }

    //случайный выбор вопроса
    public TNode pickQuestion() {
        if (nodesQuestionAllAsked()) {
            return this;
        }
        int rndQuestion = -1;
        while (true) {
            rndQuestion = random.nextInt(nodes.size());
            if (!nodes.get(rndQuestion).asked) {
                break;
            }

        }
       
        return nodes.get(rndQuestion);
    }

    //получил ответ от пользователя
    public TNode getAnswer(boolean answer) {
        if (!answer) {
            return this.parentNode.pickQuestion();
        } else {
            return pickQuestion();
        }
    }
}
