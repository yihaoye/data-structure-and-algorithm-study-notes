void quickSort(vector<int>& A, int p,int r)
{
	int q;
    if(p<r)
    {
        q=partition(A, p, r);
        quickSort(A,p,q-1);  
        quickSort(A,q+1,r);
    }
}


int partition(vector<int>& A, int p,int r)
{
    int x= A[r];
    int i=p-1;
    int j;

    for(j=p; j<=r-1; j++)
    {
        if(A[j]<=x)
        {
            i=i+1;
            swap(A[i],A[j]);
        }

    }

    swap(A[i+1],A[r]);
    return i+1;
}