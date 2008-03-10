/**
 * 
 */
package org.moreunit.util;

import org.eclipse.jdt.core.IJavaProject;
import org.moreunit.preferences.Preferences;

/**
 * @author giana
 * 13.05.2006 18:56:57
 */
public class PreferencesMock extends Preferences {
	
	private String[] prefixes;
	private String[] suffixes;
	
	public PreferencesMock(String[] prefixes, String[] suffixes) {
		this.prefixes = prefixes;
		this.suffixes = suffixes;
	}

	public String[] getPrefixes(IJavaProject javaProject) {
		return prefixes;
	}
	
	public String[] getSuffixes(IJavaProject javaProject) {
		return suffixes;
	}

	public void setPrefixes(String[] prefixes) {
		this.prefixes = prefixes;
	}

	public void setSuffixes(String[] suffixes) {
		this.suffixes = suffixes;
	}

}


// $Log: not supported by cvs2svn $
// Revision 1.2  2006/09/19 21:48:27  channingwalton
// added some tests and logging to help debug a problem
//
// Revision 1.1.1.1  2006/08/13 14:30:56  gianasista
// initial
//
// Revision 1.1  2006/06/22 20:21:44  gianasista
// package rename
//
// Revision 1.1  2006/06/19 20:11:29  gianasista
// CVS Refactoring
//
// Revision 1.1  2006/05/13 18:32:47  gianasista
// Searching for testcases for a class (based on preferences) + Tests
//