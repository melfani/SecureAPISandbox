package api.springboot.sandbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {

	@Autowired
	BlogRepository blogRespository;

	// we use autowired now
	// BlogMockedData blogMockedData = BlogMockedData.getInstance();

	@GetMapping("/blog")
	public List<Blog> index() {
		return blogRespository.findAll();
	}

	@GetMapping("/blog/{id}")
	public Blog show(@PathVariable String id) {
		int blogId = Integer.parseInt(id);
		return blogRespository.findById(blogId);
	}

	@PostMapping("/blog/search")
	public List<Blog> search(@RequestBody Map<String, String> body) {
		String searchTerm = body.get("text");
		return blogRespository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
	}

	@PostMapping("/blog")
	public Blog create(@RequestBody Map<String, String> body) {
		String title = body.get("title");
		String content = body.get("content");
		return blogRespository.save(new Blog(title, content));
	}

	@PutMapping("/blog/{id}")
	public Blog update(@PathVariable String id, @RequestBody Map<String, String> body) {
		int blogId = Integer.parseInt(id);
		// getting blog
		Blog blog = blogRespository.findById(blogId);
		blog.setTitle(body.get("title"));
		blog.setContent(body.get("content"));
		return blogRespository.save(blog);
	}

	@DeleteMapping("blog/{id}")
	public boolean delete(@PathVariable String id) {
		int blogId = Integer.parseInt(id);
		blogRespository.delete(blogRespository.findById(blogId));
		return true;
	}

}