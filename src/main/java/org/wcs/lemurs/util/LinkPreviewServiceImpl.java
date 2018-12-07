package org.wcs.lemurs.util;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Zacharie <zacharie.rakotoarinivo@gmail.com>
 */
@Service
public class LinkPreviewServiceImpl implements LinkPreviewService {

    @Override
    public List<String> getAsSingleTag(String line) {
        String[] splited = line.split(">");
        return Arrays.asList(splited);
    }
}
