package ch.epfl.osper.metadata.rest;

import ch.epfl.osper.metadata.services.TaxonomyResolver;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created by kryvych on 03/02/15.
 */
@Controller
@RequestMapping("/taxonomy")
public class TaxonomyController {

    private final TaxonomyResolver taxonomyResolver;

    @Inject
    public TaxonomyController(TaxonomyResolver taxonomyResolver) {
        this.taxonomyResolver = taxonomyResolver;
    }

    @RequestMapping(value = "/columnNames", method = RequestMethod.GET)
    public
    @ResponseBody
    String getColumnName(@RequestParam String tableName, @RequestParam Set<String> properties) {
        Collection<String> names = taxonomyResolver.getColumnNameInTableForTerm(tableName, properties);
        return Arrays.toString(names.toArray());
//        Gson gson = new Gson();
//        return gson.toJson(names);
    }


    @RequestMapping(value = "/taxonomyName", method = RequestMethod.GET)
    public
    @ResponseBody
    String getTaxonomyName(@RequestParam String columnName) {
        String name = taxonomyResolver.getTermForColumnName(columnName);
        return name;
    }


}
