
// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        A1List node = new A1List(address,size,key);

        node.next = this.next;
        this.next=node;
        node.prev=this;
        node.next.prev = node;

        // A1List head = this;
        // while(head.prev!=null) head = head.prev;
    

        // node.next=head.next;
        // head.next=node;
        // node.prev=head;
        // node.next.prev = node;

        return node;
    }

    public boolean Delete(Dictionary d) 
    {
        A1List Forward = this.next;
        A1List Backward = this.prev;
        
        if(this.key==d.key && this.address==d.address && this.size==d.size && this.prev!=null && this.next!=null){
            A1List Last = this.prev;

             if(this.prev.prev==null) {this.key=-1;this.address=-1;this.size=-1;this.prev=null;return true;}
            // if(this.next.next==null) {this.key=-1;this.address=-1;this.size=-1;this.next=null;return true;}

            this.key=Last.key;
            this.address=Last.address;
            this.size=Last.size;

            Last.key=d.key;
            Last.address=d.address;
            Last.size=d.size;
        }
        if(Forward!=null){
            while(Forward.next!=null){
                if(Forward.key==d.key && Forward.address==d.address && Forward.size==d.size){
                    A1List Last = Forward.prev;
                    Last.next = Forward.next;
                    Forward.next.prev=Last;
                    return true;
                } 
                Forward = Forward.next;
            }
        }
        if(Backward!=null){
            while(Backward.prev!=null){
                if(Backward.key==d.key && Backward.address==d.address && Backward.size==d.size){
                    A1List Last = Backward.prev;
                    Last.next = Backward.next;
                    Backward.next.prev=Last;
                    return true;
                } 
                Backward = Backward.prev;
            }
        }

        return false;
    }

    public A1List Find(int k, boolean exact)
    {   if(exact==true){
            A1List node = this.getFirst();
            if(node==null) return null;
            while(node.key!=k&& node.next.next!=null) node=node.next;
            if(node.key==k) return node;
            else return null;
        }
        else {
            A1List node = this.getFirst();
            if(node==null) return null;
            while(node.key<k && node.next.next!=null) node=node.next;
            if(node.key>=k) return node;
            else return null;
        }
    }

    public A1List getFirst()
    {   if(this.prev==null && this.next.next==null) return null;
        if(this.prev==null) return this.next;
        A1List node = this;
        while(node.prev.prev!=null) node = node.prev;
        return node;
    }
   
    public A1List getNext() 
    {   if(this.next.next==null) return null;
        return this.next;
    }

    public boolean sanity()
    {
        A1List node = this.getFirst();
        A1List p1=node;
        A1List p2=node;
        while(p1!=null && p2!=null && p2.getNext()!=null){
            p1 = p1.getNext(); //slow pointer
            p2 = p2.getNext().getNext(); // fast pointer
            if(p1==p2) return false; // returns false if cycle detected
        }

        if(node==null) return true;
        else if(node.prev.prev!=null) return false; // returns false if head.prev!=null

        while(node.next!=null){
            if(node.next.prev!=node) return false; // returns false if the given invariant doesn't hold for any node
            node=node.next; 
        }
        return true;
    }
    
    

}




