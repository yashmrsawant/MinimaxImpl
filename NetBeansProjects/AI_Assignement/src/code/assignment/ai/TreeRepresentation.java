/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code.assignment.ai;

import java.util.ArrayList;

/**
 * @author Yash M. Sawant
 */
class TreeRepresentation {
        public ArrayList<TreeRepresentation> childrens = 
                new ArrayList<>();
        private int value;
        private String id;
        private TreeRepresentation parent;
        private boolean isTerminal;
        private boolean isMaximizer;
        private boolean finalized = false;
        public void finalizeThis() {
            finalized = true;
        }
        public boolean getIfFinalized() {
            return finalized;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return this.id;
        }
        public TreeRepresentation(TreeRepresentation parent) {
            this.parent = parent;
        }
        public boolean addChild(TreeRepresentation childNode) {
            return this.childrens.add(childNode);
        }
        public void setValue(int value) {
            this.value = value;
        }
        public int getValue() {
            return this.value;
        }
        public void setIfTerminal(boolean isTerminal) {
            this.isTerminal = isTerminal;
            if(isTerminal == true) 
            	finalized = true;
        }
        public void setIfMaximizer(boolean isMaximizer) {
            this.isMaximizer = isMaximizer;
        }
        public TreeRepresentation getParent() {
            return this.parent;
        }
        public boolean isTerminal() {
            return isTerminal;
        }
        public boolean isMaximizer() {
            return isMaximizer;
        }
        public void showOff(int indentation) {
            for(int i = 0 ; i < indentation ; i++) 
                System.out.print("\t");
            System.out.println("ID : "+id);
            
            for(int i = 0 ; i < indentation ; i++) 
                System.out.print("\t");
            System.out.println("Value : "+value);
            for(TreeRepresentation child : childrens) 
                child.showOff(indentation + 1);
        }
}