import com.jamie.marsrover.{Directions, MarsGrid, Rover}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfter

class RoverTest extends AnyFunSuite with BeforeAndAfter
{

  val grid: MarsGrid = new MarsGrid(10, 10)

  test("Rover heads in the next clockwise direction when rotating clockwise") {
    val rover = new Rover(grid,(0,0))
    assert(rover.direction == Directions.headingNorth)

    val roverEast = rover.rotateClockwise()
    assert(roverEast.direction == Directions.headingEast)

    val roverSouth = roverEast.rotateClockwise()
    assert(roverSouth.direction == Directions.headingSouth)

    val roverWest = roverSouth.rotateClockwise()
    assert(roverWest.direction == Directions.headingWest)

    val roverNorth = roverWest.rotateClockwise()
    assert(roverNorth.direction == Directions.headingNorth)
  }

  test("Rover heads in the next anticlockwise direction when rotating anticlockwise") {
    val rover = new Rover(grid,(0,0))
    assert(rover.direction == Directions.headingNorth)

    val roverWest = rover.rotateAnticlockwise()
    assert(roverWest.direction == Directions.headingWest)

    val roverSouth = roverWest.rotateAnticlockwise()
    assert(roverSouth.direction == Directions.headingSouth)

    val roverEast = roverSouth.rotateAnticlockwise()
    assert(roverEast.direction == Directions.headingEast)

    val roverNorth = roverEast.rotateAnticlockwise()
    assert(roverNorth.direction == Directions.headingNorth)
  }

  test("Rover moves in direction it faces") {
    val rover = new Rover(grid,(0,0))
    val roverFwd = rover.moveForward()
    assert(roverFwd.position == (0,1))

    val roverEast = roverFwd.rotateClockwise()
    val roverEastFwd = roverEast.moveForward()
    assert(roverEastFwd.position == (1,1))

    val roverSouth = roverEastFwd.rotateClockwise()
    val roverSouthFwd = roverSouth.moveForward()
    assert(roverSouthFwd.position == (1,0))

    val roverWest = roverSouthFwd.rotateClockwise()
    val roverWestFwd = roverWest.moveForward()
    assert(roverWestFwd.position == (0,0))

    val roverNorth = roverWestFwd.rotateClockwise()
    val roverNorthFwd = roverNorth.moveForward()
    assert(roverNorthFwd.position == (0,1))

    val roverNorthFwd2 = roverNorthFwd.moveForward()
    assert(roverNorthFwd2.position == (0,2))
  }

  test("Rover moves onto other side of grid if it moves beyond it") {
    val rover = new Rover(grid,(9,9))
    val roverFwd = rover.moveForward()
    assert(roverFwd.position == (9,0))

    val roverEast = roverFwd.rotateClockwise()
    val roverEastFwd = roverEast.moveForward()
    assert(roverEastFwd.position == (0,0))
  }

  test("Shortest path can be found between two points on grid") {
    assert(Directions.shortestPath(grid, (0,0), (5,2)).length == 8)
  }

  test("Shortest path can be found between two points on grid with obstacles") {
    val rockyGrid = new MarsGrid(10, 10, Seq((0,1),(1,0),(9,2)))
    assert(Directions.shortestPath(grid, (0,0), (5,2)).length == 8)
  }
}