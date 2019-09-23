import java.io.*;

public class HW2 {

    private Node head;

    public Node getHead() {
        return head;
    }

    static class Node {
        private int data;
        private Node right;
        private Node down;

        public Node(int d) {
            data = d;
            right = null;
            down = null;
        }

    };

    static public HW2 insertRight(HW2 list, int d) {
        Node newNode = new Node(d);

        Node lastInRow = list.head;


        while (lastInRow.down != null) {
            lastInRow = lastInRow.down;
        }

        while (lastInRow.right != null)
            lastInRow = lastInRow.right;
        
        lastInRow.right = newNode;
        return list; 
    }

    static public HW2 insertDown(HW2 list, int d) {
        Node newNode = new Node(d);
        
        if (list.head == null) {
            list.head = newNode;
            return list;
        }
        Node lastInCol = list.head;
        while (lastInCol.down != null)
            lastInCol = lastInCol.down;

        lastInCol.down = newNode;
        return list; 
    }

    static public int returnRowNum(HW2 list) {
        int rowSize = 0;
        Node rowNode = list.head; 
        
        while (rowNode != null) {
            rowNode = rowNode.down;
            rowSize++;
        }
        return rowSize;
    }

    static public int returnColNum(HW2 list) {
        int colSize = 0;
        Node colNode = list.head; 
        
        while (colNode != null) {
            colNode = colNode.right;
            colSize++;
        }
        return colSize;
    }

    static public int returnIndex(HW2 list, int rowIndex, int colIndex) {
        int value = 0;
        Node temp = list.head;

        for (int i = 0; i < rowIndex; i++) {
            temp = temp.down;
        }
        for (int j = 0; j < colIndex; j++) {
            temp = temp.right;
        }

        value = temp.data;
        return value;
    }

    static public HW2 add_sub(HW2 listA, HW2 listB, int condition) {
        HW2 listC = new HW2();

        if (returnColNum(listA) != returnColNum(listB) || returnRowNum(listA) != returnRowNum(listB))
            {System.out.println("Size of two arrays are not the same!");
            return listC;
        }
        else {
            for (int a = 0; a < returnRowNum(listA);a++) {
                for (int b = 0; b < returnColNum(listA);b++) {
                    int result = 0;
                    if (condition == 1)
                        result = returnIndex(listA, a, b) + returnIndex(listB, a, b);
                    else if (condition == -1)
                        result = returnIndex(listA, a, b) - returnIndex(listB, a, b);
                    if (b == 0)
                        listC = insertDown(listC, result);
                    else 
                        listC = insertRight(listC, result);
                }
            }

        }
        return listC;
    }

    static public HW2 mult(HW2 listA, HW2 listB) {
        HW2 listC = new HW2();
        if (returnColNum(listA) != returnRowNum(listB) || returnRowNum(listA) != returnColNum(listB)) {
            System.out.println("Matrices can't be multiplied!");
                return listC;
        }
        else {
// switch places of the matrices to do mult
            if (returnRowNum(listA) == returnColNum(listB) && returnColNum(listA) != returnRowNum(listB) ) {
                HW2 temp = listA;
                listA = listB;
                listB = temp;
            }
                
            for (int a = 0; a < returnRowNum(listA);a++) { // 0, 1
                for (int b = 0; b < returnColNum(listB);b++) { // 0, 1 
                    int result = 0;

                        
                    for (int c = 0; c < returnColNum(listA);c++) 
                        result += returnIndex(listA, a, c) * returnIndex(listB, c, b);
                    
                    
                    if (b == 0) 
                        listC = insertDown(listC, result);
                    else   
                        listC = insertRight(listC, result);
                
                }
            }
        }
        return listC;
    }

    static public HW2 findCofactor(HW2 list, int rowPos, int colPos) {
        HW2 temp = new HW2();

        for (int a = 0; a < returnRowNum(list);a++) {
            int incrementB = 0;

            for (int b = 0; b < returnColNum(list);b++) {
                if (a != rowPos && b != colPos) {
                    if (incrementB == 0) {
                        temp = insertDown(temp, returnIndex(list, a, b));
                        incrementB++;
                    }
                    else 
                        temp = insertRight(temp, returnIndex(list, a, b));
                }  
            }
        }

        return temp;
    }

    static public int findDeterminant(HW2 list, int size) {
        int D = 0;

        if (size == 2) {
            int d2 = returnIndex(list,0,0) * returnIndex(list,1,1) - returnIndex(list,0,1) * returnIndex(list,1,0);
            return d2;
        }

        int sign = 1;

        for (int i = 0; i < size;i++) {
            HW2 temp = new HW2();
            temp = findCofactor(list, 0, i);
            D += sign * returnIndex(list,0,i) * findDeterminant(temp, size-1);
            sign = -sign;
        }

        return D;
    }

    static public HW2 transpose(HW2 list) {

        HW2 temp = new HW2();
        for (int a = 0; a < returnRowNum(list);a++) {
            for (int b = 0; b < returnColNum(list);b++) {
                if (b == 0)
                    temp = insertDown(temp,returnIndex(list,b,a));
                else 
                    temp = insertRight(temp,returnIndex(list,b,a)); 
            }
        }
        return temp;
    }
    static public void print(HW2 list) {
        Node colNode = list.head; 
        System.out.print("HW2: \n"); 
   
        while (colNode != null) {
            Node rowNode = colNode;
            
            while (rowNode != null) {
                System.out.print(rowNode.data);
                System.out.print(".0");
                System.out.print(' ');
                rowNode = rowNode.right;
            }
            System.out.println('\n');
            colNode = colNode.down;
            }
        }

        static public void printFile(HW2 list, BufferedWriter file) {
            Node colNode = list.head; 
            
            try {
            while (colNode != null) {
                Node rowNode = colNode;
                
                while (rowNode != null) {
                    file.write(Integer.toString(rowNode.data));
                    file.write(".0");
                    file.write(' ');
                    rowNode = rowNode.right;
                }
                file.write('\n');
                colNode = colNode.down;
                }
            } catch (IOException e) {
                System.out.println("File not found!");
            }
        }

        /////////////////////// MAIN /////////////////////////
        public static void main(String[] args) {
            HW2 head = new HW2();
            HW2 head2 = new HW2();
            int alength = args.length;
            /*
            head = insertDown(head, 2);
            head = insertRight(head, 6);
            head = insertRight(head, 2);
            head = insertDown(head, 6);
            head = insertRight(head, 2);
            head = insertRight(head, 18);
            head = insertDown(head, 17);
            head = insertRight(head, 11);
            head = insertRight(head, 20);


            head2 = insertDown(head2, 3);
            head2 = insertRight(head2, 13);
            head2 = insertRight(head2, 5);
            head2 = insertDown(head2, 4);
            head2 = insertRight(head2, 11);
            head2 = insertRight(head2, 20);
            head2 = insertDown(head2, 13);
            head2 = insertRight(head2, 18);
            head2 = insertRight(head2, 20);
            */
        
        
    try 
    {
    FileReader fr = new FileReader(args[1]);
    BufferedReader br = new BufferedReader(fr);
    String s;
    while ((s = br.readLine()) != null) {
        String temp = s;        
        int rowLength = temp.split(" ").length;
        for (int i = 0; i < rowLength; i++) {
            if (i == 0)
                head = insertDown(head, Integer.parseInt(temp.split(" ")[i]));
            else
                head = insertRight(head, Integer.parseInt(temp.split(" ")[i]));
        }
            }
    fr.close();
    }   catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (alength == 4) {
        try 
        {
        FileReader fr2 = new FileReader(args[2]);
        BufferedReader br2 = new BufferedReader(fr2);
        String s;
        while ((s = br2.readLine()) != null) {
            String temp = s;        
            int rowLength = temp.split(" ").length;
            for (int i = 0; i < rowLength; i++) {
                if (i == 0)
                    head2 = insertDown(head2, Integer.parseInt(temp.split(" ")[i]));
                else
                    head2 = insertRight(head2, Integer.parseInt(temp.split(" ")[i]));
            }
                }
                fr2.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }

            try 
                (FileWriter writer = new FileWriter(args[alength-1]);
                BufferedWriter output = new BufferedWriter(writer)) {
                    HW2 result = new HW2();
                    String a0 = args[0];
                    if (a0.equals("add")) {
                        result = add_sub(head, head2, 1);
                        printFile(result,output);
                    }
                    else if (a0.equals("sub")) {
                        result = add_sub(head,head2,-1);
                        printFile(result,output);
                    }
                    else if (a0.equals("mul")) {
                        result = mult(head, head2);
                        printFile(result,output);
                    }
                    else if (a0.equals("tra")) {
                        result = transpose(head);
                        printFile(result,output);
                    }
                    else if (a0.equals("det")) {
                        output.write(Integer.toString(findDeterminant(head, returnColNum(head)))+".0");
                    }
                output.close();
                } catch (IOException e) {
                System.err.format("IOException: %s%n", e);
                }
        }
    };



    
