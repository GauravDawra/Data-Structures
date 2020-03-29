import java.util.Arrays;
import java.util.function.*;

public class Heap{

    public static enum Type{
        minHeap,
        maxHeap
    }

    private static boolean greater(int a, int b){
        return a>=b;
    }
    private static boolean less(int a, int b){
        return a<=b;
    }

    int capacity;
    int size;
    int []arr;
    Type type;
    BiFunction<Integer, Integer, Boolean> comp;

    Heap(){
        capacity = 100001;
        size = 0;
        type = Type.maxHeap;
        arr = new int[capacity];
        chooseComp();
    }
    
    Heap(Type T){
        capacity = 100001;
        size = 0;
        type = T;
        arr = new int[capacity];
        chooseComp();
    }

    Heap(int cap){
        capacity = cap;
        size = 0;
        arr = new int[capacity];
        chooseComp();
    }

    void chooseComp(){
        if(type == Type.maxHeap){
            comp = Heap::greater;
        }
        else{
            comp = Heap::less;
        }
    }

    void checkCapacity(){
        if(size >= capacity){
            capacity = 2 * capacity;
            arr = Arrays.copyOf(arr, capacity);
        }
    }

    int parent(int ind){ return ind!=0?(ind-1)/2:0; }
    int leftChild(int ind){ return (2*ind + 1<size) ? 2*ind+1 : -1; }
    int rightChild(int ind){ return (2*ind + 2 < size) ? 2*ind + 2 : -1; }

    void swap(int ind1, int ind2){
        int t = arr[ind1];
        arr[ind1]=arr[ind2];
        arr[ind2]=t;
    }

    void heapifyUp(){
        int cur = size - 1;
        while(cur > 0){
            if(comp.apply(arr[parent(cur)], arr[cur])) break;
            swap(cur, parent(cur));
            cur = parent(cur);
        }
    }

    void heapifyDown(int index){
        int cur = index;
        while(cur<size){
            int largest = cur;
            if(leftChild(cur)!=-1 && comp.apply(arr[leftChild(cur)], arr[largest])) 
                largest = leftChild(cur);
            if(rightChild(cur)!=-1 && comp.apply(arr[rightChild(cur)], arr[largest])) 
                largest = rightChild(cur);
            if(largest == cur) break;
            
            swap(cur, largest);
            cur = largest;
        }
    }

    void push(int ele){
        arr[size] = ele;
        size++;
        checkCapacity();
        heapifyUp();
    }

    int top(){
        if(size>0) return arr[0];
        else return -1;
    }

    void pop(){
        if(size==0) return;
        arr[0] = arr[size-1];
        size--;
        heapifyDown(0);
    }

    void buildHeap(int []a){
        for(int i=0;i<a.length;i++) arr[i] = a[i];
        size = a.length;
        for(int i=parent(size-1); i>=0; i--)
            heapifyDown(i);
    }
    
    public static void main(String []args){
        
    }
}