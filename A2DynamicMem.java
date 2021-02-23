// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 

    public void Defragment() {
        if(type==2){
        BSTree dict = new BSTree();
        for(BSTree i= (BSTree) freeBlk.getFirst(); i != null; i = i.getNext()) {
            dict.Insert(i.address,i.size,i.address);
        }
        for(BSTree i = dict.getFirst();i!=null && i.getNext()!=null;) {
            if(i.key+i.size==i.getNext().key){
                BSTree nxt = i.getNext();
                int var1 = i.key;
                int var2 = i.size;
                int var3 = nxt.size;
                BSTree node1 = new BSTree(i.address,i.size,i.size);
                BSTree node2 = new BSTree(nxt.address,nxt.size,nxt.size);
                freeBlk.Delete(node1);
                dict.Delete(i);
                freeBlk.Delete(node2);
                dict.Delete(nxt);
                freeBlk.Insert(var1, var2 + var3,var2 + var3 );
                i = dict.Insert(var1,var2+var3,var1);
                //BSTree node = new BSTree(i.key,i.size+i.getNext().size,i.key);
            } else {
                i=i.getNext();
            }
        }
        } else if(type==3){
            AVLTree dict = new AVLTree();
            for(AVLTree i= (AVLTree) freeBlk.getFirst(); i != null; i = i.getNext()) {
                dict.Insert(i.address,i.size,i.address);
            }
            for(AVLTree i = dict.getFirst();i!=null && i.getNext()!=null;) {
                if(i.key+i.size==i.getNext().key){
                    AVLTree nxt = i.getNext();
                    int var1 = i.key;
                    int var2 = i.size;
                    int var3 = nxt.size;
                    AVLTree node1 = new AVLTree(i.address,i.size,i.size);
                    AVLTree node2 = new AVLTree(nxt.address,nxt.size,nxt.size);
                    freeBlk.Delete(node1);
                    dict.Delete(i);
                    freeBlk.Delete(node2);
                    dict.Delete(nxt);
                    freeBlk.Insert(var1, var2 + var3,var2 + var3 );
                    i = dict.Insert(var1,var2+var3,var1);
                //BSTree node = new BSTree(i.key,i.size+i.getNext().size,i.key);
                } else {
                    i=i.getNext();
                }
            }
        } 
        return ;
    }

    
}
