package model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * @author Karl Nicholas
 * 
 * This is the workhorse of the program. It uses java reflection
 * to get and set bean properties. Special handling is required
 * for the class field because it is a reserved java keyword. 
 * special handling is also required for the fec and bioguide_previous
 * because they are arraylists. 
 *
 */
public abstract class GovTrackBase implements GovTrackInterface {
	private final String[] savedNames;
	private static final Class<?>[] noparam = new Class[0];
	private static final Class<?>[] paramString = new Class[1];
	private static final Class<?>[] paramArrayList = new Class[1];
	private final Class<?> cls;

	public GovTrackBase(String[] names) {
		savedNames = names;
		paramString[0] = String.class;
		paramArrayList[0] = ArrayList.class;
		cls = getClass();
	}

	@SuppressWarnings("unchecked")
	public GovTrackBase(Map<String, ?> map, String[] names) {
		savedNames = names;
		paramString[0] = String.class;
		paramArrayList[0] = ArrayList.class;
		cls = getClass();

		for (String name : names) {
			try {
				Method method;
				if (name.equals("class")) {
					method = cls.getDeclaredMethod("setGclass", GovTrackBase.paramString);
				} else if (name.equals("bioguide_previous") || name.equals("fec")) {
					method = cls.getDeclaredMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), GovTrackBase.paramArrayList);
				} else {
						method = cls.getDeclaredMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), GovTrackBase.paramString);
				}
				if (name.equals("bioguide_previous") || name.equals("fec")) {
					
					if (!(map == null || map.get(name) == null) ) {
						ArrayList<String> value = (ArrayList<String>) ((ArrayList<String>) map.get(name)).clone();
						method.invoke(this, value);
					}
				} else {
					if ( !(map == null || map.get(name) == null) ) {
						String value2 = (String) map.get(name);
						method.invoke(this, value2);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public boolean compareField(String name, String value) {
		try {
			Method method;
			if (name.equals("class")) {
				method = cls.getDeclaredMethod("getGclass", GovTrackBase.noparam);
			} else if (name.equals("bioguide_previous") || name.equals("fec")) {
				method = cls.getDeclaredMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1), GovTrackBase.noparam);
			} else {
				method = cls.getDeclaredMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1), GovTrackBase.noparam);
			}
			if (name.equals("bioguide_previous") || name.equals("fec")) {
				@SuppressWarnings("unchecked")
				ArrayList<String> listValues = (ArrayList<String>) method.invoke(this, (Object[])noparam);
				if ( listValues != null ) {
					for ( String aValue: listValues ) {
						if ( aValue.equalsIgnoreCase(value)) return true;
					}
				} else {
					return false;
				}
			} else {
				String fValue = (String)method.invoke(this, (Object[])noparam);
				if ( fValue != null ) {
					if ( name.equalsIgnoreCase("start") || name.equalsIgnoreCase("end") ) {
						return fValue.substring(0, 4).equalsIgnoreCase(value);
					} else {
						return fValue.equalsIgnoreCase(value);
					}
				} else {
					return false;
				}
				
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public String toString() {
		String ret = new String( getKey() + "={");
		for (String name : savedNames) {
			try {
				Method method;
				if (name.equals("class")) {
					method = cls.getDeclaredMethod("getGclass", GovTrackBase.noparam);
				} else if (name.equals("bioguide_previous") || name.equals("fec")) {
					method = cls.getDeclaredMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1), GovTrackBase.noparam);
				} else {
					method = cls.getDeclaredMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1), GovTrackBase.noparam);
				}
				if (name.equals("bioguide_previous") || name.equals("fec")) {
					@SuppressWarnings("unchecked")
					ArrayList<String> fList = (ArrayList<String>) method.invoke(this, (Object[])noparam);
					if ( fList != null ) {
						ret = ret + name + "=" + fList + ", ";
					}
				} else {
					String str = (String) method.invoke(this, (Object[])noparam);
					if ( str != null ) {
						if (str.length() != 0) {
							ret = ret + name + "=" + str + ", ";
						}
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		ret = ret + "}";
		return ret;
	}

}
