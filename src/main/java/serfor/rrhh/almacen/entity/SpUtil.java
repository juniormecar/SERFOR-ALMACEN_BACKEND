package serfor.rrhh.almacen.entity;

import org.hibernate.query.procedure.internal.ProcedureParameterImpl;

import javax.persistence.Parameter;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;

public class SpUtil {
    private SpUtil() {
    }

    public static void enableNullParams(StoredProcedureQuery sp) {
        if (sp == null || sp.getParameters() == null)
            return;

        for (Parameter<?> parameter : sp.getParameters()) {
            ((ProcedureParameterImpl<?>) parameter).enablePassingNulls(true);
        }
    }

    public static Long toLong(Object o) {
        try {
            return Long.parseLong(String.valueOf(o));
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isNullOrEmptyNumeriCero(Object object) {
        if (object != null && !object.toString().trim().equals("")) {
            try {
                BigDecimal numero = new BigDecimal(object.toString());
                return false;
            } catch (Exception e) {
                return true;
            }
        }
        return true;
    }

    //Jaqueline Diaz Barrientos
    public static String toString(Object o) {
        if (o !=  null) {
            return o.toString();
        } else {
            return "";
        }
    }

    public static Integer toInteger(Object o) {
        if (!SpUtil.isNullOrEmptyNumeriCero(o)) {
            return Integer.parseInt(o.toString());
        } else {
            return 0;
        }
    }

    public static BigDecimal toBigDecimal(Object object) {
        if (!SpUtil.isNullOrEmptyNumeriCero(object)) {
            return new BigDecimal(object + "");
        } else {
            return BigDecimal.ZERO;
        }
    }

}
