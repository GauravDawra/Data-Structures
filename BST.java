class bstNode<T>{
    int key;
    T data;
    int numOfNodes;
    int maxHeight;
    bstNode<T> left, right;
    bstNode(){
        key = 0;
        data = null;
        numOfNodes = maxHeight = 1;
        left = right = null;
    }
    bstNode(int k, T d){
        key = k;
        data = d;
        left = null;
        right = null;
        numOfNodes = 1;
        maxHeight=1;
    }
    bstNode(int k, T d, bstNode<T> l, bstNode<T> r){
        key = k;
        data = d;
        left = l;
        right = r;
        numOfNodes = 1;
        maxHeight=1;
    }
    int max(int a, int b){
        return (a>b)?a:b;
    }
    int getMaxHeight(){
        return maxHeight;
    }

    int getImbalance(){
        int imb = 0; 
        if(this.left!=null) imb += this.left.getMaxHeight();
        if(this.right!=null) imb -= this.right.getMaxHeight();
        return imb;
    }

    void setHeight(){
        maxHeight = 1;
        if(this.left!=null) maxHeight = max(maxHeight, this.left.getMaxHeight() + 1);
        if(this.right!=null) maxHeight = max(maxHeight, this.right.getMaxHeight() + 1);
    }

    void setNumOfNodes(){
        numOfNodes = 1;
        if(this.left!=null) numOfNodes += this.left.numOfNodes;
        if(this.right!=null) numOfNodes += this.right.numOfNodes;
    }

    void setLeft(bstNode<T> x){
        this.left = x;
        this.setHeight();
        this.setNumOfNodes();
    }

    void setRight(bstNode<T> x){
        this.right = x;
        this.setHeight();
        this.setNumOfNodes();
    }
}
public class BST<T>{
    private int size;
    private bstNode<T> root;
    BST(){
        size = 0;
        root = null;
    }
    BST(int []a){
        for(int i=0;i<a.length;i++)
            this.Insert(a[i]);
    }
    int size(){
        return size;
    }

    bstNode<T> root(){
        return root;
    }

    bstNode<T> rightRotate(bstNode<T> cur){
        if(cur.left==null) return cur;
        bstNode<T> t = cur.left;
        bstNode<T> temp = t.right;
        cur.setLeft(temp);
        t.setRight(cur);
        return t;
    }

    bstNode<T> leftRotate(bstNode<T> cur){
        if(cur.right == null) return cur;
        bstNode<T> t = cur.right;
        bstNode<T> temp = t.left;
        cur.setRight(temp);
        t.setLeft(cur);
        return t;
    }

    bstNode<T> insertUtil(bstNode<T> cur, int K, T D){
        if(cur == null){
            bstNode<T> n = new bstNode<T> (K, D);
            root = n;
            size++;
            return root;
        }
        if(cur.key >= K && cur.left == null){
            bstNode<T> n = new bstNode<T> (K, D);
            cur.setLeft(n);
            size++;
            return cur;
        }
        if(cur.key < K && cur.right == null){
            bstNode<T> n = new bstNode<T> (K, D);
            cur.setRight(n);
            size++;
            return cur;
        }
        if(cur.key >= K){
            bstNode<T> r = insertUtil(cur.left, K, D);
            cur.setLeft(r);
            int imb = cur.getImbalance();
            if(imb>1){
                if(r.key >= K) cur = rightRotate(cur);
                else {
                    r = leftRotate(r);
                    cur.setLeft(r);
                    cur = rightRotate(cur);
                }
            }
        }

        else{
            bstNode<T> r = insertUtil(cur.right, K, D);
            cur.setRight(r);
            int imb = cur.getImbalance();
            if(imb<-1){
                if(r.key < K) cur = leftRotate(cur);
                else{
                    r = rightRotate(r);
                    cur.setRight(r);
                    cur = leftRotate(cur);
                }
            }
        }
        cur.setHeight();
        cur.setNumOfNodes();
        return cur;
    }

    void Insert(int K, T D){
        root = insertUtil(root, K, D);
    }

    void Insert(int K){
        root = insertUtil(root, K, null);
    }

    T get(int k){
        bstNode<T> cur = root;
        while(cur!=null){
            if(cur.key==k) return cur.data;
            if(cur.key<k) cur=cur.right;
            else cur = cur.left;
        }
        return null;
    }

    void set(int k, T d){
        bstNode<T> cur = root;
        while(cur!=null){
            if(cur.key==k) {cur.data = d; return;}
            if(cur.key<k) cur=cur.right;
            else cur = cur.left;
        }
    }

    int orderOf(int k){
        int sum = 0;
        bstNode<T> cur = root;
        while(cur!=null){
            if(cur.key==k){
                sum += (cur.left!=null) ? cur.left.numOfNodes + 1 : 1;
                return sum;
            }
            if(cur.key < k){
                sum += (cur.left!=null) ? cur.left.numOfNodes + 1 : 1;
                cur = cur.right;
            }
            else cur = cur.left;
        }
        return sum;
    }

    int findByOrder(int ind){
        if(ind<0 || ind > size-1) return 0;
        bstNode<T> cur = root;
        int curSum = (cur.left!=null) ? cur.left.numOfNodes : 0;
        // System.out.println(curSum);
        while(cur!=null){
            if(ind == curSum){
                return cur.key;
            }
            if(ind < curSum){
                // bstNode<T> parent = cur;
                cur = cur.left;
                curSum -= (cur.right!=null) ? cur.right.numOfNodes + 1 : 1;
            }
            else{
                cur = cur.right;
                curSum += (cur.left!=null) ? cur.left.numOfNodes + 1 : 1;
            }
        }
        return 0;
    }

    void inOrder(bstNode<T> cur){
        if(cur == null) return;
        inOrder(cur.left);
        System.out.println(cur.key);
        inOrder(cur.right);
    }

    void preOrder(bstNode<T> cur){
        if(cur == null) return;
        System.out.println(cur.key);
        preOrder(cur.left);
        preOrder(cur.right);
    }

    void postOrder(bstNode<T> cur){
        if(cur == null) return;
        postOrder(cur.left);
        postOrder(cur.right);
        System.out.println(cur.key);
    }



    public static void main(String []args){
        
    }
}