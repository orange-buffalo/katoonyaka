package co.katoonyaka.web.admin.controllers;

import co.katoonyaka.domain.admin.PhotoUsageStatistics;
import co.katoonyaka.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/api/uploadcare")
@ResponseBody
@Scope("session")
public class UploadcareAdminController {

    @Autowired
    private PhotoService photoService;

    private PhotoUsageStatistics data;

    @RequestMapping(method = RequestMethod.GET)
    public PhotoUsageStatistics getUcFilesList() {
        return getData();
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public PhotoUsageStatistics refresh() {
        data = null;
        return getUcFilesList();
    }

    @RequestMapping(value = "/fix", method = RequestMethod.GET)
    public PhotoUsageStatistics fix() {
        photoService.removeExternalResourcesByIds(data.getUnusedExternalPhotos());
        return refresh();
    }

    private PhotoUsageStatistics getData() {
        if (data == null) {
            data = photoService.getPhotoUsageStatistics();
        }
        return data;
    }

}
