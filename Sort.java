public class Sort{
    public static int[] merge(int []a, int []b){
        int []c = new int[a.length+b.length];
        int p1=0, p2=0;
        int i=0;
        while(p1<a.length && p2<b.length){
            if(a[p1]<b[p2]){
                c[i]=a[p1];
                p1++;
            }
            else{
                c[i]=b[p2];
                p2++;
            }
            i++;
        }
        while(p1<a.length){
            c[i]=a[p1];
            p1++;
            i++;
        }
        while(p2<b.length){
            c[i]=b[p2];
            p2++;
            i++;
        }
        return c;
    }
    public static int[] merge_sort(int []a){
        if(a.length==1) return a;
        int []a1=new int[a.length/2];
        int []a2=new int[a.length - a.length/2];
        int i=0;
        for(int j=0;j<a.length/2;j++, i++){
            a1[j]=a[i];
        }
        for(int j=0;j<a.length - a.length/2;j++, i++){
            a2[j]=a[i];
        }
        a1=merge_sort(a1);
        a2=merge_sort(a2);
        int []sub=merge(a1, a2);
        return sub;
    }
    public static void quick_sort(int []a, int h, int k){
        if(k<=h) return;
        // int p = a[h];
        int i=h+1;
        int j=k;
        while(i<=j){
            if(a[i]<a[i-1]){
                int t=a[i];
                a[i]=a[i-1];
                a[i-1]=t;
                i++;
            }
            else{
                int t=a[i];
                a[i]=a[j];
                a[j]=t;
                j--;
            }
        }
        
        quick_sort(a, h, i-2);
        quick_sort(a, i, k);
    }

    public static int[] heapSort(int []arr){
        Heap h = new Heap(Heap.Type.minHeap);
        h.buildHeap(arr);
        int[] newArr = new int[arr.length];
        int i=0;
        while(i<arr.length){
            newArr[i] = h.top();
            h.pop();
            i++;
        }
        return newArr;
    }

    public static void main(String[] args){
        System.out.println("Welcome to the sort class");
    }
}