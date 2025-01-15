package reviewers.server.domain.contents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.contents.entity.Actor;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.repository.ActorRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorAppearancesService actorAppearancesService;

    public void create(String actors, Contents contents) {
        Set<String> names = parseNames(actors);
        saveNewActors(names);
        List<Actor> actorsList = findActorsByNames(names);
        actorAppearancesService.linkActorsToContents(actorsList, contents);
    }

    private Set<String> parseNames(String actors) {
        return Optional.ofNullable(actors)
                .filter(a -> !a.isBlank())
                .map(a -> Arrays.stream(a.split(",\\s*"))
                        .collect(Collectors.toSet()))
                .orElse(Set.of());
    }

    private void saveNewActors(Set<String> names) {
        Set<String> existingNames = actorRepository.findByNameIn(names).stream()
                .map(Actor::getName)
                .collect(Collectors.toSet());

        List<Actor> newActors = names.stream()
                .filter(name -> !existingNames.contains(name))
                .map(name -> Actor.builder().name(name).build())
                .toList();
        actorRepository.saveAll(newActors);
    }

    private List<Actor> findActorsByNames(Set<String> names) {
        return actorRepository.findByNameIn(names);
    }
}
