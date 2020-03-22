#pragma once

#include<vector>
using namespace std;
class unionFind{
    vector<int> parent, rank;
    int size;
public:
    unionFind(int n){
        size=n;
        rank.assign(n, 0);
        parent.assign(n, 0);
        for(int i=0;i<n;i++) parent[i]=i;
    }
    int findSet(int i);
    bool isSameSet(int i, int j);
    void unionSet(int i, int j);
};

int unionFind::findSet(int i){
    return (parent[i] == i) ? i : (parent[i] = findSet(parent[i]));
}

bool unionFind::isSameSet(int i, int j){
    return findSet(i) == findSet(j);
}

void unionFind::unionSet(int i, int j){
    if(isSameSet(i, j)) return;
    int x = findSet(i), y = findSet(j);
    if(rank[x] > rank[y])
        parent[y] = x;
    else{
        parent[x] = y;
        if(rank[x] == rank[y]) rank[y]++;
    }
}