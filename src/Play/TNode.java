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
    public ArrayList<TNode> nodes;
    public String question;
    public boolean asked = false;
    public int treeDepth = 0;

    public static FileWriter writer;
    public static FileReader reader;
    public static Random random = new Random();
    public static int counter = 0;

    public TNode() {
        parentNode = null;
        question = "Загадайте животное";
        nodes = new ArrayList();
    }

    public TNode(TNode parent) {
        parentNode = parent;
        nodes = new ArrayList();
    }

    public TNode(TNode parent, String q, int depth) {
        parentNode = parent;
        nodes = new ArrayList();
        question = q;
        treeDepth = depth;
    }

    public void addNode() {
        nodes.add(new TNode(this));
    }

    public void addNode(String q, int depth) {
        nodes.add(new TNode(this, q, depth));
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
                currentNode.addNode(line, ++level);
                for (int i = 0; i < level; i++) {
                    System.out.print("  ");

                }
                System.out.print(level + "" + line);
                System.out.println("");
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
//        if (parentNode == null) {
//            return false;
//        }
        for (TNode node : nodes) {
            if (!node.asked) {
                return false;
            }
        }
        return true;
    }

    public boolean autoGO() {
        TNode temp = pickQuestion();
       // System.out.println(temp.question);
        temp.asked = true;
        if ((nodesQuestionAllAsked()) && treeDepth < 3) {
            temp.asked = false;
            return true;
        } else {
            temp.asked = false;
            return false;
        }
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
