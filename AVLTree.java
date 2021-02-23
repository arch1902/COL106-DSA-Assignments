// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    private int getHeight(AVLTree node){
        if(node==null) return -1;
        else return node.height;
    }

    private boolean isGreater(Dictionary a, Dictionary b){
        if(a.key>b.key) return true;
        else if(a.key==b.key && a.address>b.address) return true;
        else if(a.key==b.key && a.address==b.address && a.size>b.size) return true;
        return false;
    }

    private void update(AVLTree node){
        node.height = 1 + Math.max(getHeight(node.left),getHeight(node.right));
    }

    private AVLTree min(AVLTree node){
        while(node.left!=null) node = node.left;
        return node;
    }

    private AVLTree getRoot(){
        if(this.parent==null) return this.right;
        else{
            AVLTree curr = this;
            while(curr.parent.parent!=null) curr=curr.parent;
            return curr;
        }
    }

    private AVLTree rotateRight(AVLTree node){
        AVLTree x = node.left;
        AVLTree y = x.right;
        AVLTree z = node.parent;
        x.right = node;
        node.parent = x;
        node.left=y;
        if(y!=null) y.parent=node;
        x.parent=z;
        update(node);
        update(x);

        return x;

    }

    private AVLTree rotateLeft(AVLTree node ){
        AVLTree x = node.right;
        AVLTree y = x.left;
        AVLTree z = node.parent;
        x.left = node;
        node.parent = x;
        node.right=y;
        if(y!=null) y.parent=node;
        x.parent=z;
        update(node);
        update(x);

        return x;

    }

    private void rebalanceTree(AVLTree node){
        node = rebalance(node);
        if(node.parent!=null && node.parent.parent!=null) rebalanceTree(node.parent);
    }

    private AVLTree rebalance(AVLTree node){
        update(node);
        int a=getHeight(node.right);
        int b=getHeight(node.left);
        int balance=a-b;

        if(Math.abs(balance)<=1) return node;

        if(balance <-1 && getHeight(node.left.left)>=getHeight(node.left.right)){
            if(node.parent.right==node) node.parent.right=rotateRight(node);
            else node.parent.left = rotateRight(node);
        } 
        else if(balance <-1 && getHeight(node.left.left)<getHeight(node.left.right)){
            node.left = rotateLeft(node.left);
            if(node.parent.right==node) node.parent.right=rotateRight(node);
            else node.parent.left = rotateRight(node);
        } 
        else if(balance >1 && getHeight(node.right.left)<=getHeight(node.right.right)){
            if(node.parent.left==node) node.parent.left = rotateLeft(node);
            else node.parent.right = rotateLeft(node);
        } 
        else if(balance >1 && getHeight(node.right.left)>getHeight(node.right.right)){
            node.right = rotateRight(node.right);
            if(node.parent.left==node) node.parent.left = rotateLeft(node);
            else node.parent.right = rotateLeft(node);
        }

        return node;
    }

    public AVLTree Insert(int address, int size, int key){
        if(this.parent==null) {
            if(this.right==null){
                AVLTree node = new AVLTree(address,size,key);
                this.right = node;
                node.parent = this;
                rebalanceTree(node);
                return node;
            } else{
                return this.right.Insert1(address,size,key);
            }
        } else {
            return this.getRoot().Insert1(address,size,key);
        }
    }
    
    private AVLTree Insert1(int address, int size, int key) 
    { 
        

        if(isGreater(this,new AVLTree(address,size,key))){
            if(this.left==null){
                AVLTree node = new AVLTree(address,size,key);
                this.left=node;
                node.parent = this;
                rebalanceTree(node);
                return node;
            } else {
                return this.left.Insert1(address,size,key);
            }
        } else {
            if(this.right==null){
                AVLTree node = new AVLTree(address,size,key);
                this.right=node;
                node.parent = this;
                rebalanceTree(node);
                return node;
            } else {
                return this.right.Insert1(address,size,key);
            }
        }
    }

    public boolean Delete(Dictionary e){
        if(this.parent == null ){
            if(this.right==null) return false;
            else return this.right.Delete1(e);
        } else {
            return this.getRoot().Delete1(e);
        }
    }

    private boolean Delete1(Dictionary e)
    {
        
        if(isGreater(this,e)) {
            if(this.left==null) return false;
            else return this.left.Delete1(e);
        } else if(isGreater(e,this)){
            if(this.right==null ) return false;
            else return this.right.Delete1(e);
        } else if(this.key==e.key && this.address == e.address && this.size == e.size){
            if(this.left==null && this.right == null){
                if(this.parent.left==this) this.parent.left = null;
                else this.parent.right = null;
                rebalanceTree(this.parent);
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
                rebalanceTree(this.parent);
                return true;
            } else if(this.right == null){
                if(this.parent.right==this){
                    this.parent.right = this.left;
                    this.left.parent = this.parent;
                } else {
                    this.parent.left = this.left;
                    this.left.parent = this.parent;
                }
                rebalanceTree(this.parent);
                return true;
            } else {
                AVLTree curr = min(this.right);
                
                this.key = curr.key;
                this.address=curr.address;
                this.size=curr.size;

                return this.right.Delete1(curr);
            }

        }
        return false;
    }
        
    public AVLTree Find(int k, boolean exact)
    { 
        if(this.getRoot()==null) return null;
        else return this.getRoot().Find1(k,exact);
    }

    private AVLTree Find1(int key, boolean exact)
    {   if(exact == true){
            
            if(key<this.key) {
                if(this.left==null) return null;
                else return this.left.Find1(key,true);
            }    
            else if(key>this.key) {
                if(this.right==null) return null;
                return this.right.Find1(key,true);
            }
            else {

                if(this.left!=null){
                    AVLTree curr = this.left.Find1(key,true);
                    if(curr!=null) return curr;   
                } 
                return this;
            }

                // AVLTree curr = this;
                // AVLTree ans = null;
                // while(curr!=null){
                //     if(curr.key<key) curr= curr.right;
                //     else if(curr.key>key) curr=curr.left;
                //     else {
                //         if(ans==null || isGreater(ans,curr)) ans=curr;
                //         curr=curr.left;
                //     }
                // }
                // return ans;


        } else {

                //ALGO-1

                // AVLTree node = this.getFirst();
                // while(node!=null && node.key<key ) node = node.getNext();
                // return node;

                //ALGO-2

                // if(this.key<key){
                //     if(this.right==null) return null;
                //     return this.right.Find1(key,false);
                // } 
                // else{
                //     if(this.left==null) return this;
                //     else if(max(this.left).key<key) return this;
                //     else return this.left.Find1(key,false);
                // }

                //ALGO-3

                AVLTree curr = this;
                AVLTree ans = null;
                while(curr!=null){
                    if(curr.key<key) curr= curr.right;
                    else {
                        if(ans==null || isGreater(ans,curr)) ans=curr;
                        curr=curr.left;
                    }
                }
                return ans;
            
        }
    }


    public AVLTree getFirst(){
        if(this.getRoot()==null) return null;
        else return this.getRoot().getFirst1();
    }

    private AVLTree getFirst1()
    {   
        if(this.parent==null) {
            if(this.right==null) return null;
            else return this.right.getFirst();
        } else {
            AVLTree curr = this;
            while(curr.left!=null) curr=curr.left;
            return curr;
        } 
        
    }

    public AVLTree getNext()
    {
        if(this.parent==null ) return null;
        
        else if(this.right==null){
            if(this.parent!=null && this.parent.right==this) {
                AVLTree curr = this;
                while(curr.parent!=null && curr.parent.left!=curr) curr=curr.parent;
                return curr.parent;
            }
            else if(this.parent!=null ) return this.parent;
            return null;
        } else {
            AVLTree curr = this.right;
            while(curr.left!=null) curr=curr.left;
            return curr;
        }
    }

    public boolean sanity(){
        if(this.getRoot()==null) return true;
        else return this.getRoot().sanity1();
    }

    private boolean sanity1()
    {   
        if(Math.abs( getHeight(this.left) - getHeight(this.right))>1) return false;
        
        if(this.left!=null && this.right==null){
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

