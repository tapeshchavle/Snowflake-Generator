@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlShortenerService service;

    @GetMapping("/shorten")
    public String shorten() {
        return service.generateShortUrl();
    }
}
