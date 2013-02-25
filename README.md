geotiff-iosp
============

GeoTIFF IOSP for NetCDF-Java

GeoTIFF-IOSP is a plugin for the Unidata [NetCDF-Java Library](http://www.unidata.ucar.edu/software/netcdf-java/) that allows access to GeoTIFF images using the NetCDF-Java Common Data Model (CDM).  This IOSP can be also be used with the Unidata [THREDDS Data Server](http://www.unidata.ucar.edu/projects/THREDDS/tech/TDS.html)(TDS) to vend and access GeoTIFF images without have to convert them to another CDM supported format.  This allows GeoTIFF file content to be accessed via OPeNDAP, WCS, WMS, NcISO and other TDS services. 

###Installation

  * Clone this repository:

      `git clone https://github.com/tkunicki-usgs/geotiff-iosp.git`
    
  * In the root directory of the clone build the project with Maven.  This will build both the geotiff-iosp jar and a TDS war.

	  `cd geotiff-iosp ; mvn install`

  * A TDS war file with the injected GeoTIFF IOSP and its required dependencies is the then available in `thredds/target/thredds.war`

  * Advanced users will also be able to include the GeoTIFF-IOSP in maven projects utilizing NetCDF-Java by declaring the GeoTIFF-IOSP as a dependency.  GeoTIFF data will then be available for access using the NetCDF-Java CDM
 
###Usage

  * You can now use GeoTIFF files just as you would NetCDF files -- you can aggregate them or add metadata using NcML, or just add   `<include wildcard="*.tif"/>` to your DatasetScan to pick them up.  
  
  * Here is an example: http://geoport-dev.whoi.edu/thredds/dodsC/usgs/data1/rsignell/catalog.html?dataset=usgs/data1/rsignell/sample.tif

###Notes

Proper interpretation of the numerous representations of geo-referencing allowed for in the GeoTIFF format requires full access to an EPSG database. To minimize dependencies a lightweight EPSG database was implemented backed by EPSG CSV dumps obtained from the libgeotiff project.  Future development could allow for the use of GeoTools or GeoToolKit geo-referencing implementations but this may prove difficult to implement cleanly as complete interpretation of all potential GeoTIFF geo-referencing information would require access to EPSG data beyond what the GeoTools and GTK referencing abstractions allow for (read as: "might need use of private API or depend on implementation details that change between GeoTools or GTK releases")

Installation without the use of maven is difficult due to the number of required dependencies and caveats concerning their use in different projects and platforms.