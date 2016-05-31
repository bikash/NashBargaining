package org.cloudbus.cloudsim.examples;

import java.io.IOException;

/*------- Exercise 2 -------
Assume you are given two relations R = {a,b} and S = {c,d}, which can fit in main memory completely.
Now, consider that you are given a join query with the following join predicate: R.a < S.d.
Implement a variant of the sort-merge join to perform such a join query and explain your solution.*/

/*
 * Solution:
 * Main idea: 
 * Step 1: If R and S are not sorted we need to sort at first.
 * Step 2: Once R and S are sorted, we need to scan each element only once and compare the element. If they 
 * are equal we join them.
 * Step 3: else ignore the element. 
 * 
 */

/*
 * Advantage of sort-merge join in each element sorted need to scan only once.
 * 
 * 
 */
import java.util.*;


public class Test2 {
	
	// Test the join algorithm
	public static void main ( String args[] ) throws Exception {
		Relation r = new Relation();
		r.insert("Age:26 ID:23");
		r.insert("Age:17 ID:33");
		r.insert("Age:28 ID:20");
		r.insert("Age:28 ID:22");
		//System.out.println(" *** r *** ");
		//System.out.println(r);
		Relation s = new Relation();
		s.insert("Class:A ID:33");
		s.insert("Class:E ID:20");
		s.insert("Class:B ID:23");
		s.insert("Class:C ID:22");
		s.insert("Class:D ID:17");
		s.insert("Class:E ID:15");
		s.insert("Class:F ID:20");
		//System.out.println(" *** s *** ");
		//System.out.println(s);
		

		// Step1: Sorting
		Relation R = r.sorted("ID");
		Relation S = s.sorted("ID");
		
		
		System.out.println(" After Sorting *** s *** ");
		System.out.println(S);
		System.out.println("After Sorting *** r *** ");
		System.out.println(R);
		
		//Step 2 Merging
		Relation join = mergeJoin(R,S,"ID");
		System.out.println(" ***JOIN *** ");
		System.out.println(join);
	}
	

	// Implementation of the Merge Join Algorithm
	public static Relation mergeJoin ( Relation left, Relation right, String attribute ) {
		// merge sort
		int i = 0;
		int j = 0;
		Relation output = new Relation();
		List<Tuple>  r = left.tuples;
		List<Tuple>  s = right.tuples;
		
		Tuple R = left.tuples.get(0);
		Tuple S = right.tuples.get(0);
		
		
		while(i<(r.size()) && j<(s.size()))
		{
			if(R.compareAttr(R,S,attribute)>0)
				j = j+1;
			else if(R.compareAttr(R,S,attribute) <0)
				i = i+1;
			else /* r[B] = s[B]*/
				if(i>=r.size() || j>=s.size())
					break;
				
				S = right.tuples.get(j);
				R = left.tuples.get(i);
			
				
				while(j<s.size() && (R.compareAttr(R,S,attribute)==0) )
				{			
					output.insert(R,S);
					j=j+1;
					if(j<s.size())
						S = right.tuples.get(j);
				}		
		}

		return output;
	}
	
}

// Simple class representing tuples in a relation
class Tuple {		
	
	 Map<String,String> attributes;
	
	public String getAttributeValue(String name) {
		return attributes.get(name);
	}

	public Tuple ( ) {
		attributes = new HashMap<String,String>();
	}
	
	public Tuple ( String data ) {
		attributes = new HashMap<String,String>();
		String atts[] = data.split(" ");
		for ( String att : atts ) attributes.put(att.split(":")[0], att.split(":")[1]);
	}
	public int compareAttr(Tuple t1, Tuple t2, String attribute)
	{
		int res = String.CASE_INSENSITIVE_ORDER.compare(t1.getAttributeValue(attribute) , t2.getAttributeValue(attribute) );
		//System.out.println(" Comparision result "+ t2.getAttributeValue(attribute));
    	return res == 0 ? t1.getAttributeValue(attribute).compareTo(t2.getAttributeValue(attribute)) : res;
	}
	
}

// Simple class implementing a Relation
class Relation {
	
	List<Tuple> tuples;

	public Relation(List<Tuple> tuples) { this.tuples = tuples; }

	public Relation() { this.tuples = new ArrayList<Tuple>(); }
	
	public void insert ( Tuple t  ) { tuples.add(t); }
	
	public void insert ( Tuple t_r1, Tuple t_r2 ) { 
		Tuple t = new Tuple();
		for ( Map.Entry<String,String> ent : t_r1.attributes.entrySet() ) 
			t.attributes.put(ent.getKey(), ent.getValue());
		for ( Map.Entry<String,String> ent : t_r2.attributes.entrySet() ) 
			t.attributes.put(ent.getKey(), ent.getValue());
		tuples.add(t); 
	}
	
	public void insert ( String t ) { tuples.add(new Tuple(t)); }
	
	
	Relation sorted ( final String attribute ) {

		List<Tuple> sorted = new ArrayList<Tuple>();
		sorted.addAll(tuples);

		// Sorting
		Collections.sort(sorted, new Comparator<Tuple>() {
		        @Override
		        public int compare(Tuple t1, Tuple t2)
		        {
		        	return  t1.getAttributeValue(attribute).compareTo(t2.getAttributeValue(attribute));
		        }
		    });
		
		
		//Collections.sort(sorted,new AttributeComparator());
		return new Relation(sorted);
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		List<String> atts = new ArrayList<String>();
		for ( Tuple tuple : tuples ) {
			if(i++ == 0)  {
				for ( String s : tuple.attributes.keySet() ) {
					atts.add(s);
					sb.append(s);
					sb.append(" ");
				}
				sb.append("\n");
			}			
			for ( String att : atts ) {
				sb.append(tuple.getAttributeValue(att));
				sb.append(" ");				
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
