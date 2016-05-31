/*
 * Author:bikash
 * Date: May 31, 2016
 * 
 * Copyright (c) 2016-bikash
 * Project: Nash Bargaining theory for resource allocation
 * Description:
 * 
 *  
 * 
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */

package example.NashBargaining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Host;


/**
 * The Class Hosts.
 */
public final class Hosts implements Iterable<Host> {

  /** The host_list. */
  private final List<Host> host_list = new LinkedList<Host>();

	/**
	 * Instantiates a new hosts.
	 *
	 * @param hosts the hosts
	 */
	public Hosts(List<? extends Host> hosts) {
		this.host_list.addAll(hosts);
	}
	
	/**
	 * Adds the.
	 *
	 * @param host the host
	 * @return true, if successful
	 */
	public boolean add(Host host){
		return this.host_list.add(host);
	}
	
	/**
	 * Removes the.
	 *
	 * @param host2Remove the host2 remove
	 * @return true, if successful
	 */
	public boolean remove(Host host2Remove){
		return this.host_list.remove(host2Remove);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Host> iterator() {
		return get().iterator();
	}

	/**
	 * Gets the.
	 *
	 * @return the list
	 */
	public List<Host> get() {
		return Collections.unmodifiableList(this.host_list);
	}

	/**
	 * Gets the with minimum number of pes equals.
	 *
	 * @param numberOfPes the number of pes
	 * @return the with minimum number of pes equals
	 */
	public Host getWithMinimumNumberOfPesEquals(int numberOfPes) {
		List<Host> hosts = this.orderedAscByAvailablePes().get();

		for (int i = 0; i < hosts.size(); i++) {
			if (hosts.get(i).getNumberOfFreePes() >= numberOfPes) {
				return hosts.get(i);
			}
		}
		return null;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size() {
		return this.host_list.size();
	}

	/**
	 * Ordered asc by available pes.
	 *
	 * @return the hosts
	 */
	public Hosts orderedAscByAvailablePes() {
		List<Host> list = new ArrayList<Host>(this.host_list);

		Collections.sort(list, new Comparator<Host>() {
			@Override
			public int compare(Host o1, Host o2) {
				return Integer.valueOf(o1.getNumberOfFreePes()).compareTo(
						o2.getNumberOfFreePes());
			}
		});
		return new Hosts(list);
	}
}