package ucar.nc2.iosp.geotiff.cs;

import javax.measure.converter.UnitConverter;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import ucar.ma2.DataType;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.iosp.geotiff.epsg.GTEllipsoid;
import ucar.nc2.iosp.geotiff.epsg.GTGeogCS;
import ucar.nc2.iosp.geotiff.epsg.GTPrimeMeridian;

public class GeogCSGridMappingAdapter implements GridMappingAdapter {

    private GTGeogCS geogCS;

    public GeogCSGridMappingAdapter(GTGeogCS geogCS) {
        this.geogCS = geogCS;
    }

    @Override
    public int getCode() {
        return geogCS.getCode();
    }

    @Override
    public synchronized Variable generateGridMappingVariable(NetcdfFile netCDFFile, int index) {
        
        Variable variable = new Variable(netCDFFile, null, null, "crs" + index);
        variable.setDataType(DataType.INT);
        variable.setIsScalar();
        variable.addAttribute(new Attribute("grid_mapping_name", "latitude_longitude"));

        augmentCRSVariable(variable);

        return variable;
    }

    public void augmentCRSVariable(Variable variable) {
        
        GTPrimeMeridian meridian = geogCS.getPrimeMeridian();
        GTEllipsoid ellipsoid = geogCS.getEllipsoid();
        
        UnitConverter meridianConverter = meridian.getUnitOfMeasure().getUnit().getConverterTo(NonSI.DEGREE_ANGLE);
        UnitConverter ellipsoidConverter = ellipsoid.getUnitOfMeasure().getUnit().getConverterTo(SI.METRE);
        
        variable.addAttribute(new Attribute("longitude_of_prime_meridian", meridianConverter.convert(meridian.getLongitude())));

        double semiMajor = ellipsoid.getSemiMajorAxis();
        double semiMinor = ellipsoid.getSemiMinorAxis();
        double inverse = ellipsoid.getInverseFlattening();
        if (!Double.isNaN(semiMajor)) {
            variable.addAttribute(new Attribute("semi_major_axis", ellipsoidConverter.convert(semiMajor)));
        }
        if (!Double.isNaN(semiMinor)) {
            variable.addAttribute(new Attribute("semi_minor_axis", ellipsoidConverter.convert(semiMinor)));
        }
        if (!Double.isNaN(inverse)) {
            // no conversion! inverse flattening is unit-less!
            variable.addAttribute(new Attribute("inverse_flattening", inverse));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeogCSGridMappingAdapter other = (GeogCSGridMappingAdapter) obj;
        if (this.geogCS != other.geogCS && (this.geogCS == null || !this.geogCS.equals(other.geogCS))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.geogCS != null ? this.geogCS.hashCode() : 0);
        return hash;
    }
}
