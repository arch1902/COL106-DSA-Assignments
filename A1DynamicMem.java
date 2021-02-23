// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        if(blockSize == 0) return -1;
        Dictionary node = freeBlk.Find(blockSize, false);
        //A1List node = (A1List) find;
        if(node==null) return -1;
        int addr = node.address;
        if(node.key==blockSize) {
            allocBlk.Insert(node.address,node.key,node.address);
            freeBlk.Delete(node);
            return addr;
        }
        allocBlk.Insert(node.address,blockSize,node.address);
        freeBlk.Insert(node.address+blockSize,node.key-blockSize,node.key-blockSize);
        freeBlk.Delete(node);

        return addr;
    } 
    
    public int Free(int startAddr) {
        Dictionary node = allocBlk.Find(startAddr,true);
        if(node==null) return -1;
        freeBlk.Insert(node.address,node.size,node.size);
        allocBlk.Delete(node);
        return 0 ;

    }

    
}
