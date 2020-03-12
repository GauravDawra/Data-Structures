public class linked_list{
    static class node{
        public int data;
        public node next, prev;
        node(){
            data=0;
            next=null;
            prev = null;
        }
        node(int d, node n){
            next=n;
            data=d;
            prev = null;
        }
        node(int d, node n, node p){
            next=n;
            data=d;
            prev=p;
        }
    }
    static class queue{
        node front, rear;
        int size;
        queue(){
            front = null;
            rear = null;
            size=0;
        }
        void push(int d){
            node n = new node(d, null);
            if(front == null)
                front = n;
            else
                rear.next = n;
            rear = n;
            size++;
        }
        void pop(){
            if(rear == null)
                System.out.println("Underflow");
            else{
                front = front.next;
                if(front == null) rear = null;
                System.gc();
                size--;
            }
        }
    }
    static class stack{
        node top;
        int size;
        stack(){
            top = null;
            size=0;
        }
        void push(int d){
            node n = new node(d, top);
            top = n;
            size++;
        }
        void pop(){
            if(top == null)
                System.out.println("Underflow");
            else {
                top = top.next;
                size--;
                System.gc();
            }
        }
    }
    static class dLinkedList{
        node front, rear;
        int size;
        dLinkedList(){
            front = rear = null;
            size=0;
        }
        void insert(int pos, int d){
            if(pos<-1 || pos>=size) return;
            node ptr = front;
            for(int i=0;i<pos;i++) ptr=ptr.next;
            
            node n;
            if(pos==-1)
                n = new node(d, front, null);
            else 
                n = new node(d, ptr.next, ptr);
            
            if(pos!=-1) ptr.next = n;
            if(n.next!=null) n.next.prev = n;
            
            if(pos == size-1) rear = n;
            if(pos == -1) front = n;
            size++;
        }
        void push_back(int d){
            node n = new node(d, null, rear);
            if(rear!=null) rear.next = n;
            rear = n;
            if(front==null) front = n;
            size++;
        }
        void push_front(int d){
            this.insert(-1, d);
        }
        void delete(int pos){
            if(pos<0 || pos>=size) return;
            node ptr = front;
            node prevPtr = null;
            for(int i=0;i<pos;i++){
                prevPtr = ptr;
                ptr = ptr.next;
            }
            if(prevPtr!=null) prevPtr.next = ptr.next;
            if(ptr.next!=null) ptr.next.prev = prevPtr;
            if(pos==0) front = front.next;
            if(pos==size-1) rear = rear.prev;
            size--;
            System.gc();
        }
        void pop_back(){
            if(rear == null) return;
            rear = rear.prev;
            if(rear == null) front=null;
            else rear.next = null;
            System.gc();
            size--;
        }
        void pop_front(){
            if(front == null) return;
            front = front.next;
            if(front==null) rear=null;
            else front.prev = null;
            System.gc();
            size--;
        }
    }
}