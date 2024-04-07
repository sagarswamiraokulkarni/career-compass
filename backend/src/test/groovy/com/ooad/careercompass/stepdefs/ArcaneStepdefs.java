package csci.ooad.arcane.stepdefs;

import csci.ooad.arcane.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

public class ArcaneStepdefs {
    private Arcane arcane;
    private ObserverMock bddObserver;

    @Given("I have a game with the following attributes:")
    public void iHaveAGameWithTheFollowingAttributes(DataTable dataTable) {
        List<List<String>> row = dataTable.cells();
        int numberOfRooms = Integer.parseInt(row.get(0).get(1));
        int numberOfAdventurers = Integer.parseInt(row.get(1).get(1));
        int numberOfKnights = Integer.parseInt(row.get(2).get(1));
        int numberOfCowards = Integer.parseInt(row.get(3).get(1));
        int numberOfGluttons = Integer.parseInt(row.get(4).get(1));
        int numberOfCreatures = Integer.parseInt(row.get(5).get(1));
        int numberOfDemons = Integer.parseInt(row.get(6).get(1));
        int numberOfFoodItems = Integer.parseInt(row.get(7).get(1));

        Maze maze = Maze.newBuilder().createFullyConnectedRooms(numberOfRooms).createAndAddAdventurers(numberOfAdventurers).createAndAddKnights(numberOfKnights)
                .createAndAddCowards(numberOfCowards).createAndAddGluttons(numberOfGluttons).createAndAddCreatures(numberOfCreatures).createAndAddDemons(numberOfDemons)
                .createAndAddFoodItems(numberOfFoodItems).distributeRandomly().build();
        this.arcane = new Arcane(new MazeAdapter(maze));
    }


    @When("I play the game")
    public void iPlayTheGame() {
        bddObserver = new ObserverMock();
        arcane.attach(bddObserver, List.of(EventType.GameStart, EventType.Death, EventType.AllAdventurersDead, EventType.AllCreaturesDead, EventType.GameOver));
        arcane.play();
        assert bddObserver.getEvents().get(0).equals("Starting play...");

    }

    @Then("I should be told that either all the adventurers or all of the creatures have died")
    public void iShouldBeToldThatEitherAllTheAdventurersOrAllOfTheCreaturesHaveDied() {
        List<String> events = bddObserver.getEvents();
        assert (events.get(events.size() - 2).contains("All adventurers are dead") || events.get(events.size() - 2).contains("All creatures are dead"));
        assert (events.get(events.size() - 1).contains("Adventurers left alive were:") || events.get(events.size() - 1).contains("Creatures left alive were:"));
        assert ((this.arcane.getTotalAdventurersAlive() == 0 && this.arcane.getTotalCreaturesAlive() > 0) || (this.arcane.getTotalAdventurersAlive() > 0 && this.arcane.getTotalCreaturesAlive() == 0));
    }

    @And("the game should be over")
    public void theGameShouldBeOver() {
        assert bddObserver.getEvents().get(bddObserver.getEvents().size() - 1).startsWith("The game is over.");
    }

}
