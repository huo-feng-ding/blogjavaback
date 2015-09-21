package com.duxiu.scala

import com.simone.scala.GrabBlog
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner 

@RunWith(classOf[JUnitRunner])
class BlogTest extends Specification {
 
   
   (new GrabBlog).grabBlog()
 
 }
