package com.jamie.marsrover

import Directions._

class Rover(val grid: MarsGrid,
            val position: (Int, Int) = (0, 0),
            val direction: ((Int, Int)) => (Int, Int) = headingNorth)
{
  assert(position._1 >= 0, "Rover X position must be a positive integer!")
  assert(position._2 >= 0, "Rover Y position must be a positive integer!")

  def moveForward(): Rover =
  {
    new Rover(
      grid,
      modTuple(direction(position), (grid.width, grid.height)),
      direction
    )
  }

  def rotateClockwise(): Rover =
  {
    new Rover(
      grid,
      position,
      clockwise(Math.floorMod(clockwise.indexOf(direction) + 1, clockwise.length))
    )
  }

  def rotateAnticlockwise(): Rover =
  {
    new Rover(
      grid,
      position,
      clockwise(Math.floorMod(clockwise.indexOf(direction) - 1, clockwise.length))
    )
  }

  def autoPilot(destination: (Int, Int)): Unit = {
    //Displays on console using a text representation of steps taken to autopilot Rover to destination

    val routeToDest = shortestPath(grid, position, destination)
    val stringGrid = grid.grid.map(col => col.map(square => if (square) "[X]" else "[ ]"))
    for (pos <- routeToDest)
      {
        println("\n\n")
        val roverGrid = stringGrid.updated(pos._1, stringGrid(pos._2).updated(pos._2, "[R]"))
        roverGrid.foreach(col => println(col))
      }
  }
}