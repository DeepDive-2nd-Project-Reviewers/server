package reviewers.server.domain.contents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.contents.entity.Actor;
import reviewers.server.domain.contents.entity.ActorAppearances;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.repository.ActorAppearancesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActorAppearancesService {

    private final ActorAppearancesRepository actorAppearancesRepository;

    public void linkActorsToContents(List<Actor> actors, Contents contents) {
        actors.forEach(actor -> {
            ActorAppearances appearances = ActorAppearances.builder()
                    .actor(actor)
                    .contents(contents)
                    .build();
            actorAppearancesRepository.save(appearances);
        });
    }
}
