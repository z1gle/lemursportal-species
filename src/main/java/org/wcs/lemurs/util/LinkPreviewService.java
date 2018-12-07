package org.wcs.lemurs.util;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Zacharie <zacharie.rakotoarinivo@gmail.com>
 */

@Service
public interface LinkPreviewService {
    
    public List<String> getAsSingleTag(String line);
}
