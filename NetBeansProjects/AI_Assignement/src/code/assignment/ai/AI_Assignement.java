/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code.assignment.ai;

/**
 * @author Yash M. Sawant
 */
public class AI_Assignement {

    /**
     * @param args the command line arguments
     */
    static final int maxiOfMax = 999;
    static final int miniOfMin = -999;
    static java.io.BufferedReader br = new java.io.BufferedReader(new 
            java.io.InputStreamReader(System.in));
    static int i = 0;
    static boolean showed = false;
    static boolean automation = false;
    static final String[] turnString = {"Maximizer's Turn", "Minimizer's Turn"};
    static int turnNumber = 0;
    public static String inputHelper(int index) {

        /**
         * A is the root node
         */
        String[] inputs = {
            "A", "1", "0", "1", "B1", "0", "1", "C1",
            "1", "-6", "1", "C2", "1", "33", "0", "1",
            "B2", "0", "1", "C3", "1", "-2", "1", "C4",
            "1", "-3", "1", "C5", "1", "12", "0", "1",
            "B3", "0", "1", "C6", "1", "1", "0", "0"
        };
        if (automation) {
            System.out.println(inputs[index]);
            return inputs[index];
        } else {
            try {
                return br.readLine();
            } catch (Exception e) {
            }
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
        TreeRepresentation root = new TreeRepresentation(null);
        System.out.println("Enter the 1 if you want automation");
        if (Integer.parseInt(br.readLine()) == 1)
            automation = true;
        System.out.println("Please give some name to root node");
        root.setId(inputHelper(i++));
        System.out.println("Enter 1 if the current Game starts with Maximizer");
        if (Integer.parseInt(inputHelper(i++)) == 1) {
            root.setIfMaximizer(true);
            root.setValue(miniOfMin);
        } else {
            root.setIfMaximizer(false);
            turnNumber = 1;
            root.setValue(maxiOfMax);
        }
        createTree(root);
        minimax(root, 0);
        root.showOff(0);
        System.out.println("Solution : ");
        showOptimalPath(root);
    }
    public static void createTree(TreeRepresentation treeNode) throws Exception {
        /**
         * function to create tree recursively
         */
        System.out.println("Enter 1 if the current treeNode " + treeNode.getId()
                + " is Terminal");
        if (Integer.parseInt(inputHelper(i++)) == 1) {
            treeNode.setIfTerminal(true);
            System.out.println("Enter the Value of the current treeNode");
            treeNode.setValue(Integer.parseInt(inputHelper(i++)));
            if (treeNode.getParent() != null && treeNode.getParent().isMaximizer()) {
                treeNode.setIfMaximizer(false);
            } else {
                treeNode.setIfMaximizer(true);
            }
        } else {
            treeNode.setIfTerminal(false);
            if (treeNode.getParent() != null
                    && treeNode.getParent().isMaximizer()) {
                treeNode.setIfMaximizer(false);
                treeNode.setValue(maxiOfMax);
            } else if (treeNode.getParent() != null) {
                treeNode.setIfMaximizer(true);
                treeNode.setValue(miniOfMin);
            }
            while (true) {
                System.out.println("Enter 1 to enter more child node of "
                        + treeNode.getId());
                if (Integer.parseInt(inputHelper(i++)) == 1) {
                    TreeRepresentation child = new TreeRepresentation(treeNode);
                    treeNode.addChild(child);
                    System.out.println("Please give some name to child node of "
                            + treeNode.getId());
                    child.setId(inputHelper(i++));
                    createTree(child);
                } else {
                    break;
                }
            }
        }
    }
    /**
     * @param node is the TreeRepresentation node
     * @param minimax is the value of the parent node to be calculated
     */
    public static void minimax(TreeRepresentation node, int minimax) {
        /**********************************************************************
	 **if the node is terminal and level has maximizer turn then          *   
         **the parent will have a min {all the child}                         *
         **and similarly for the minimizer turn then the parent will have     *
         **max {all the child}                                                *
	 **********************************************************************
	 */
        if (!node.getIfFinalized()) {
            for (TreeRepresentation tr : node.childrens) {
                minimax(tr, tr.getValue());
            }
        }
        if (!node.isTerminal()) {
            node.finalizeThis();
            minimax = node.getValue();
        }
        if (node.isTerminal() || node.getIfFinalized()) {
            /**
             * Checking <code>node.getParent() != null</code> always to 
             * check that if the node is root node or not. There inherits some flaw 
             * in class TreeRepresentation architecture.
             */
            if (node.getParent() != null && minimax < node.getParent().getValue()
                    && node.isMaximizer()) {
                node.getParent().setValue(minimax);
            } else if (node.getParent() != null && minimax
                    > node.getParent().getValue() && !node.isMaximizer()) {
                node.getParent().setValue(minimax);
            }
        } else {
            
            node.setValue(node.childrens.get(0).getValue());
        }
    }
    public static void showOptimalPath(TreeRepresentation node) {
        if(showed)
            return;       
        int optimalValue = 0;
        TreeRepresentation inNode = null;
        if (node.isMaximizer()) {
            optimalValue = miniOfMin;
            for (TreeRepresentation child : node.childrens) {
                if (optimalValue < child.getValue()) {
                    optimalValue = child.getValue();
                    inNode = child;
                }
            }
        } else {
            optimalValue = maxiOfMax;
            for (TreeRepresentation child : node.childrens) {
                if (optimalValue > child.getValue()) {
                    optimalValue = child.getValue();
                    inNode = child;
                }
            }
        }
        if (node.isTerminal()) {
            System.out.println(node.getId());
            showed = true;
        } else {
            System.out.print(node.getId() + "(" + turnString[turnNumber] + ")" + "-> ");
            turnNumber = (turnNumber + 1) % 2;
        }
        showOptimalPath(inNode);
    }
}