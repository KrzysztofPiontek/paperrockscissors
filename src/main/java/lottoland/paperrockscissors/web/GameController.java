package lottoland.paperrockscissors.web;

import lottoland.paperrockscissors.domain.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class GameController {

    private static final String HOME_PATH = "home";
    private static final String STATS_PATH = "stats";
    private static final String ROOT_REDIRECT = "redirect:/";

    @Autowired
    private GameService gameService;

    @RequestMapping
    public String home(Model model, HttpServletRequest request) {

        List<GameService.SingleGameStats> singleGameStatistics =
                gameService.getPlayerStatistics(request.getSession().getId());
        model.addAttribute("stats", singleGameStatistics);
        return HOME_PATH;
    }

    @RequestMapping("/play")
    public String playRound(HttpServletRequest request) {

        gameService.playRound(request.getSession().getId());
        return ROOT_REDIRECT;
    }

    @RequestMapping("/reset")
    public String resetPlayer(HttpServletRequest request) {

        gameService.resetPlayer(request.getSession().getId());
        return ROOT_REDIRECT;
    }

    @RequestMapping("/stats")
    public String showStats(Model model) {

        model.addAttribute("generalStats", gameService.getGeneralStatistics());
        return STATS_PATH;
    }
}
