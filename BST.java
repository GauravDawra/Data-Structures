class bstNode<T>{
    public int key;
    public T data;
    private int numOfNodes;
    private int maxHeight;
    public bstNode<T> left, right;
    public bstNode(){
        key = 0;
        data = null;
        numOfNodes = maxHeight = 1;
        left = right = null;
    }
    public bstNode(int k, T d){
        key = k;
        data = d;
        left = null;
        right = null;
        numOfNodes = 1;
        maxHeight=1;
    }
    public bstNode(int k, T d, bstNode<T> l, bstNode<T> r){
        key = k;
        data = d;
        left = l;
        right = r;
        numOfNodes = 1;
        maxHeight=1;
    }

    private int max(int a, int b){
        return (a>b)?a:b;
    }

    public int maxHeight(){
        return maxHeight;
    }

    public int numOfNodes(){
        return numOfNodes;
    }

    public int getImbalance(){
        int imb = 0; 
        if(this.left!=null) imb += this.left.maxHeight();
        if(this.right!=null) imb -= this.right.maxHeight();
        return imb;
    }

    public void setHeight(){
        maxHeight = 1;
        if(this.left!=null) maxHeight = max(maxHeight, this.left.maxHeight() + 1);
        if(this.right!=null) maxHeight = max(maxHeight, this.right.maxHeight() + 1);
    }

    public void setNumOfNodes(){
        numOfNodes = 1;
        if(this.left!=null) numOfNodes += this.left.numOfNodes();
        if(this.right!=null) numOfNodes += this.right.numOfNodes();
    }

    public void setLeft(bstNode<T> x){
        this.left = x;
        this.setHeight();
        this.setNumOfNodes();
    }

    public void setRight(bstNode<T> x){
        this.right = x;
        this.setHeight();
        this.setNumOfNodes();
    }
}

public class BST<T>{
    private int size;
    public bstNode<T> root;
    public BST(){
        size = 0;
        root = null;
    }
    public BST(int []a){
        for(int i=0;i<a.length;i++)
            this.insert(a[i]);
    }
    public int size(){
        return size;
    }

    public bstNode<T> root(){
        return root;
    }

    private bstNode<T> rightRotate(bstNode<T> cur){
        if(cur.left==null) return cur;
        bstNode<T> t = cur.left;
        bstNode<T> temp = t.right;
        cur.setLeft(temp);
        t.setRight(cur);
        return t;
    }

    private bstNode<T> leftRotate(bstNode<T> cur){
        if(cur.right == null) return cur;
        bstNode<T> t = cur.right;
        bstNode<T> temp = t.left;
        cur.setRight(temp);
        t.setLeft(cur);
        return t;
    }

    private bstNode<T> insertUtil(bstNode<T> cur, int K, T D){
        if(cur == null){
            bstNode<T> n = new bstNode<T> (K, D);
            root = n;
            size++;
            return root;
        }
        if(cur.key == K){
            cur.data = D;
            return cur;
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

    public void insert(int K, T D){
        root = insertUtil(root, K, D);
    }

    public void insert(int K){
        root = insertUtil(root, K, null);
    }
    
    public bstNode<T> deleteUtil(bstNode<T> cur, int k){
        if(cur == null) return null;
        if(cur.key < k){
            cur.setRight(deleteUtil(cur.right, k));
        }
        else if(cur.key > k){
            cur.setLeft(deleteUtil(cur.left, k));
        }
        else{
            if(cur.right == null){
                return cur.left;
            }
            if(cur.left == null) return cur.right;
            bstNode<T> sub = cur.left;
            while(sub.right != null){
                sub = sub.right;
            }
            cur.key = sub.key;
            cur.data = sub.data;
            bstNode<T> r = deleteUtil(cur.left, sub.key);
            cur.setLeft(r);
        }
        int imb = cur.getImbalance();
        if(imb<-1){
            bstNode<T> gt = cur.right;
            if(gt.right!=null){
                if(gt.left == null || gt.right.maxHeight() > gt.left.maxHeight())
                    cur = leftRotate(cur);
            }
            else if(gt.right == null || gt.left.maxHeight() > gt.right.maxHeight()) {
                gt = rightRotate(gt);
                cur.setRight(gt);
                cur = leftRotate(cur);
            }
        }
        else if(imb > 1){
            bstNode<T> gt = cur.left;
            if(gt.left!=null){
                if(gt.right == null || gt.left.maxHeight() > gt.right.maxHeight())
                    cur = rightRotate(cur);
            }
            else if( gt.left == null || gt.right.maxHeight() > gt.left.maxHeight()){
                gt = leftRotate(gt);
                cur.setLeft(gt);
                cur = rightRotate(cur);
            }
        }
        cur.setHeight();
        cur.setNumOfNodes();
        return cur;
    }

    void delete(int k){
        root = deleteUtil(root, k);
        size--;
    }

    public T get(int k){
        bstNode<T> cur = root;
        while(cur!=null){
            if(cur.key==k) return cur.data;
            if(cur.key<k) cur=cur.right;
            else cur = cur.left;
        }
        return null;
    }

    public void set(int k, T d){
        bstNode<T> cur = root;
        while(cur!=null){
            if(cur.key==k) {cur.data = d; return;}
            if(cur.key<k) cur = cur.right;
            else cur = cur.left;
        }
    }

    public int orderOf(int k){
        int sum = 0;
        bstNode<T> cur = root;
        while(cur!=null){
            if(cur.key==k){
                sum += (cur.left!=null) ? cur.left.numOfNodes() + 1 : 1;
                return sum;
            }
            if(cur.key < k){
                sum += (cur.left!=null) ? cur.left.numOfNodes() + 1 : 1;
                cur = cur.right;
            }
            else cur = cur.left;
        }
        return sum;
    }

    public int findByOrder(int ind){
        if(ind<0 || ind > size-1) return 0;
        bstNode<T> cur = root;
        int curSum = (cur.left!=null) ? cur.left.numOfNodes() : 0;
        while(cur!=null){
            if(ind == curSum){
                return cur.key;
            }
            if(ind < curSum){
                cur = cur.left;
                curSum -= (cur.right!=null) ? cur.right.numOfNodes() + 1 : 1;
            }
            else{
                cur = cur.right;
                curSum += (cur.left!=null) ? cur.left.numOfNodes() + 1 : 1;
            }
        }
        return 0;
    }

    public boolean isMapped(int k){
        bstNode<T> cur = root;
        while(cur != null){
            if(cur.key == k) return true;
            if(cur.key < k) cur = cur.right;
            else cur = cur.left;
        }
        return false;
    }

    public void inOrder(bstNode<T> cur){
        if(cur == null) return;
        inOrder(cur.left);
        System.out.println(cur.key);
        inOrder(cur.right);
    }

    public void preOrder(bstNode<T> cur){
        if(cur == null) return;
        System.out.println(cur.key);
        preOrder(cur.left);
        preOrder(cur.right);
    }

    public void postOrder(bstNode<T> cur){
        if(cur == null) return;
        postOrder(cur.left);
        postOrder(cur.right);
        System.out.println(cur.key);
    }

    public static void main(String []args){
        
    }
}