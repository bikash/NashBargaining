package org.cloudbus.cloudsim.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*Write a program that, given two sorted arrays, A and B, merges B into A in sorted order.
A has a large enough buffer at the end to hold B, their sizes (n and m) are part of the input.

For example:
A: 4 5 8 _ _  (n = 3)
B: 1 7 (m = 2)
Result:  A: 1 4 5 7 8

Explain the complexity of your program with one or two sentences.*/


/* solution
 * 
 * The two array is merge and sort using HeapSort. As the time complexity by the heap
 * sort is O(nlogn) and memory consumption is 1.
 */
public class Test {
	
	
	public static void merge( int[] A, int[] B, int na, int mb )
    {
        int total_size = na + mb;
        int len_b = mb - 1;
        for( int i = na; i < total_size; i++ )
        {
            A[i] = B[len_b--];     
        }
    }
    public static void main( String[] args ) throws NumberFormatException, IOException
    {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	int n,m;  
    	System.out.println("Enter the size of array A: ");
    	n = Integer.parseInt(br.readLine());
    	System.out.println("Enter the size of array B: ");
    	m = Integer.parseInt(br.readLine());
    	
    	// initiliazed array A and B
        int[] a = {1,3,5,7,5, 1,8,2,3,7,5,7,9};
        int[] b = {2,4,6,8,1,2,9,1,2};
        int len =n+m;
        //Merge array A into B
        merge( a, b, n, m );
        
        //Heap sort 
        HeapSort.sort(a);
        for( int i = 0; i < len ; i++ )
        {
            System.out.print( a[i] );
        }
        System.out.println();
    }
}
