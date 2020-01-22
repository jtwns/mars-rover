package com.jamie.marsrover

import scala.util.Random

class MarsGrid(val width: Int, val height: Int, mountains: Seq[(Int, Int)] = Seq())
{
  //Will set given grid cells to True, indicating an non-traversable mountain range
  val grid: Seq[Seq[Boolean]] = Seq.tabulate(width, height)((x, y) => mountains.contains((x, y)))
}