package com.yourcodereview.iosif.task_2;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RequestController {
    private final ConfigurableApplicationContext appContext;

    public RequestController(ConfigurableApplicationContext appContext) {
        this.appContext = appContext;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public ShortLinkResponse getShortLink(@RequestBody ShortLinkRequest request){
        ResourceRepository rep = appContext.getBean(ResourceRepository.class);
        Resource resource;
        List<Resource> resources = rep.findByOriginal(request.getOriginal());
        if(resources.size() != 0){
            resource = resources.get(0);
        }
        else {
            resource = new Resource(request.getOriginal());
            resource = rep.save(resource);
            resource.setLink("/l/" + resource.getId());
            rep.save(resource);
        }
        return ResourceAdapter.createShortLinkResponse(resource);
    }

    @RequestMapping(value = "/l/{id}", method = RequestMethod.GET)
    public ModelAndView redirect( @PathVariable Long id, HttpServletRequest request){
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        ResourceRepository rep = appContext.getBean(ResourceRepository.class);
        Optional<Resource> resource = rep.findById(id);
        if(resource.isEmpty()){
            return new ModelAndView("");
        }
        resource.get().setCount(resource.get().getCount() + 1);
        rep.save(resource.get());
        String url = resource.get().getOriginal();
        return new ModelAndView("redirect:" + url);
    }

    @RequestMapping(value = "/stats/l/{id}", method = RequestMethod.GET)
    public StatisticsResponse statistics (@PathVariable Long id){
        ResourceRepository rep = appContext.getBean(ResourceRepository.class);
        Iterable<Resource> iterator = rep.findAll(Sort.by("count").descending());
        long rank = 0;
        for (Resource resource : iterator) {
            rank++;
            if(resource.getId().equals(id)){
                return ResourceAdapter.createStatisticsResponse(resource, rank);
            }
        }
        return new StatisticsResponse();
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public List<StatisticsResponse> commonStatistics (@RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "count") Integer count){
        ResourceRepository rep = appContext.getBean(ResourceRepository.class);
        Sort sort = Sort.by("count").descending();
        Pageable pageable = PageRequest.of(page, count, sort);
        Page<Resource> currentPage = rep.findAll(pageable);
        ArrayList<StatisticsResponse> responses = new ArrayList<>();
        long rank =(long) page * (long) count;
        for(Resource resource : currentPage){
            rank++;
            responses.add(ResourceAdapter.createStatisticsResponse(resource, rank));
        }
        return responses;
    }
}
