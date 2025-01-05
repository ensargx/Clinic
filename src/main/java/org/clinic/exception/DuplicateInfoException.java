package org.clinic.exception;

import org.clinic.lang.Language;
import org.clinic.util.Format;

public class DuplicateInfoException extends RuntimeException {
    private final Object[] formatList;
    public DuplicateInfoException(String formatMessage, Object ... formatList) {
        super(formatMessage);
        this.formatList = formatList;
    }

    @Override
    public String getMessage() {
        if ( formatList == null ) {
            return Language.Get( super.getMessage() );
        } else {
            String fmt = Language.Get( super.getMessage() );
            return Format.FormatMessage(fmt, formatList);
        }
    }
}
