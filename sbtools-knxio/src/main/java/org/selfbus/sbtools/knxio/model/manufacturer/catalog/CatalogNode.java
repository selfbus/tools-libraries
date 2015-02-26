package org.selfbus.sbtools.knxio.model.manufacturer.catalog;

import java.util.List;

/**
 * Interface for catalogs and catalog sections that can have child sections.
 */
public interface CatalogNode
{
   /**
    * @return The list of child catalog sections, or null if no child sections exist.
    */
   List<CatalogSection> getSections();
}
