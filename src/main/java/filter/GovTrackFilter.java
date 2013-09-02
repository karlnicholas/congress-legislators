package filter;

import java.util.*;
import model.*;

/**
 * 
 * @author Karl Nicholas
 * 
 * An interface that defines the basic chain-of-responsibility 
 * design pattern
 *
 */
public interface GovTrackFilter
{
    void doFilter(ArrayList<GovTrackEntry> p0, GovTrackEntry p1);
}
