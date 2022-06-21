package com.yourcodereview.iosif.task_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController {
    private final ConfigurableApplicationContext appContext;

    public RequestController(ConfigurableApplicationContext appContext) {
        this.appContext = appContext;
    }

    @RequestMapping("/")
    public ShortLinkResponse getShortLink(@RequestBody ShortLinkRequest request){
        ResourceRepository rep = appContext.getBean(ResourceRepository.class);
        Resource resource;
        List<Resource> resources = rep.findByOriginal(request.getOriginal());
        if(resources.size() != 0){
            resource = resources.get(0);
            resource.setCount(resource.getCount() + 1);
            rep.save(resource);
        }
        else {
            resource = new Resource(request.getOriginal());
            resource = rep.save(resource);
            resource.setLink("/l/" + resource.getId());
            rep.save(resource);
        }
        return ResourceAdapter.createShortLinkResponse(resource);
    }
}
