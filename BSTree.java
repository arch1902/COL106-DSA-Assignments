// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    private BSTree getRoot(){
        if(this.parent==null) return this.right;
        else{
            BSTree curr = this;
            while(curr.parent.parent!=null) curr=curr.parent;
            return curr;
        }
    }

    private boolean isGreater(Dictionary a, Dictionary b){
        if(a.key>b.key) return true;
        else if(a.key==b.key && a.address>b.address) return true;
        else if(a.key==b.key && a.address==b.address && a.size>b.size) return true;
        return false;
    }

    public BSTree Insert(int address, int size, int key) 
    {   
        if(this.parent == null){
            if(this.right==null){
                BSTree node = new BSTree(address,size,key);
                this.right = node;
                node.parent = this;
                return node;
            } else{
                return this.right.Insert(address,size,key);
            }
        } else {
            if(isGreater(this,new BSTree(address,size,key))){
                if(this.left==null){
                    BSTree node = new BSTree(address,size,key);
                    this.left = node;
                    node.parent = this;
                    return node;
                } else{
                    return this.left.Insert(address,size,key);
                }
            } else {
                if(this.right == null){
                    BSTree node = new BSTree(address,size,key);
                    this.right = node;
                    node.parent = this;
                    return node;
                } else{
                    return this.right.Insert(address,size,key);
                }
            }
        }

    }

    public boolean Delete(Dictionary e)
    {   
        if(this.parent == null ){
            if(this.right==null) return false;
            else return this.right.Delete(e);
        }
        if(isGreater(this,e)) {
            if(this.left==null) return false;
            else return this.left.Delete(e);
        } else if(isGreater(e,this)){
            if(this.right==null ) return false;
            else return this.right.Delete(e);
        } else if(this.key==e.key && this.address == e.address && this.size == e.size){
            if(this.left==null && this.right == null){
                if(this.parent.left==this) this.parent.left = null;
                else this.parent.right = null;
                return true;
            }
            else if(this.left==null){
                if(this.parent.left==this){
                    this.parent.left = this.right;
                    this.right.parent = this.parent ;
                } else {
                    this.parent.right = this.right;
                    this.right.parent = this.parent;
                }
                return true;
            } else if(this.right == null){
                if(this.parent.right==this){
                    this.parent.right = this.left;
                    this.left.parent = this.parent;
                } else {
                    this.parent.left = this.left;
                    this.left.parent = this.parent;
                }
                return true;
            } else {
                BSTree curr = this.right;
                while(curr.left!=null) curr = curr.left;
                this.key = curr.key;
                this.address=curr.address;
                this.size=curr.size;
                return this.right.Delete(curr);
            }

        }
        return false;
    }
    public BSTree Find(int key, boolean exact){
        if(this.getRoot()==null) return null;
        else return this.getRoot().Find1(key,exact);
    }
        
    private BSTree Find1(int key, boolean exact)
    {   if(exact == true){
            if(this.parent==null){
                if(this.right==null) return null;
                else return this.right.Find1(key,true);
            }
            if(key<this.key) {
                if(this.left==null) return null;
                else return this.left.Find1(key,true);
            }    
            else if(key>this.key) {
                if(this.right==null) return null;
                return this.right.Find1(key,true);
            }
            else {
                if(this.left!=null && this.left.Find1(key,true)!=null) return this.left.Find1(key,true);
                else return this;
            }

        } else {
            //if(this.Find(key,true)!=null) return this.Find(key,true);
            
                // if(this.parent==null){
                //     if(this.right==null) return null;
                //     else return this.right.Find(key,false);
                // }
                // if(key<this.key) {
                //     if(this.left==null) return this;
                //     else return this.left.Find(key,false);
                // }    
                // else if(key>this.key) {
                //     if(this.right==null) return null;
                //     return this.right.Find(key,false);
                // }
                // else {
                //     return this;
                // }
                // if(this.key<key && this.right!=null) return this.right.Find1(key,false);
                // else if(this.key<key && this.right==null) return null;
                // else if(this.key>)
                BSTree node = this.getFirst();
                while(node!=null && node.key<key ) node = node.getNext();
                return node;
            
        }
    }

    public BSTree getFirst(){
        if(this.getRoot()==null) return null;
        else return this.getRoot().getFirst1();
    }

    private BSTree getFirst1()
    {   
        if(this.parent==null) {
            if(this.right==null) return null;
            else return this.right.getFirst();
        } else {
            BSTree curr = this;
            while(curr.left!=null) curr=curr.left;
            return curr;
        } 
        
    }

    public BSTree getNext()
    { 
        if(this.parent==null ) return null;
        
        else if(this.right==null){
            if(this.parent!=null && this.parent.right==this) {
                BSTree curr = this;
                while(curr.parent!=null && curr.parent.left!=curr) curr=curr.parent;
                return curr.parent;
            }
            else if(this.parent!=null ) return this.parent;
            return null;
        } else {
            BSTree curr = this.right;
            while(curr.left!=null) curr=curr.left;
            return curr;
        }
    }

    public boolean sanity(){
        if(this.getRoot()==null) return true;
        else return this.getRoot().sanity1();
    }

    private boolean sanity1()
    {   if(this.left!=null && this.right==null){
            if(this.left.parent!=this) return false;
            if(isGreater(this.left,this)) return false;
            return this.left.sanity1();
        } else if(this.right!=null && this.left==null){
            if(this.right.parent!=this) return false;
            if(isGreater(this,this.right)) return false;
            return this.right.sanity1();
        } else if(this.left!=null && this.right!=null){
            if(this.left.parent!=this) return false;
            if(isGreater(this.left,this)) return false;
            if(this.right.parent!=this) return false;
            if(isGreater(this,this.right)) return false;
            return this.left.sanity1() && this.right.sanity1();
        }
        return true;
    }

    

    

}

 


