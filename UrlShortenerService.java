@Service
public class UrlShortenerService {

    @Autowired
    private SnowflakeIdGenerator idGenerator;

    public String generateShortUrl() {
        long id = idGenerator.nextId();
        return Base62Util.encode(id);
    }
}
