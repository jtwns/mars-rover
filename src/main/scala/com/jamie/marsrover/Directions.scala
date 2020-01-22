package com.jamie.marsrover

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Queue}

object Directions
{
  //Using object to declare direction functions our rover supports, taking and returning XY positions, as well as
  //useful utilities

  type Direction = ((Int, Int)) => (Int, Int)

  val headingNorth: Direction = (t: (Int, Int)) => (t._1, t._2 + 1)
  val headingEast:  Direction = (t: (Int, Int)) => (t._1 + 1, t._2)
  val headingSouth: Direction = (t: (Int, Int)) => (t._1, t._2 - 1)
  val headingWest:  Direction = (t: (Int, Int)) => (t._1 - 1, t._2)

  //Defining clockwise as a series of functions representing each direction as a transformation on XY coordinates
  val clockwise: Seq[Direction] = Seq(headingNorth,
    headingEast,
    headingSouth,
    headingWest)

  //Scala modulo doesn't handle negatives
  def safeMod(x: Int, y: Int): Int =
  {
    ((x % y) + y) % y
  }

  //This allows me to modulo a set of coordinates with the grid dimensions for a wrap-around behaviour
  def modTuple(t1: (Int, Int), t2: (Int, Int)): (Int, Int) =
  {
    (safeMod(t1._1, t2._1), safeMod(t1._2, t2._2))
  }

  def shortestPath(grid: MarsGrid, from: (Int, Int), to: (Int, Int)): Seq[(Int, Int)] =
  {
    //BFS applied to bit maze data structure of the MarsGrid type
    //Uses mutable data structures to simplify implementation

    val q = Queue(from)
    val visited = ListBuffer(from)

    //As we're not using a real graph, this tracks the path to a given grid cell
    val routes: mutable.Map[(Int, Int), Seq[(Int, Int)]] = mutable.Map(from -> Seq(from))

    while (q.nonEmpty)
    {
      val current = q.dequeue()
      if (current == to) return routes(current)

      //Check each direction we've defined as traversable and whether there's a mountain range present
      for (direction <- clockwise)
      {
        //Peek in the given direction
        val (x, y) = modTuple(direction(current), (grid.width, grid.height))

        //If this grid cell is traversable, store route, mark as visited and add this to queue
        if (!grid.grid(x)(y) && !visited.contains((x, y)))
        {
          routes.put((x, y), routes(current) :+ (x, y))
          visited.append((x, y))
          q.enqueue((x, y))
        }
      }
    }
    Nil
  }
}