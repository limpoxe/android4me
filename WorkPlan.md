# Overall goals #

  * Implement most of Android classes & most of methods inside them
  * Make it work reasonably fast

# Plan #

I propose this pieces of Android to be implemented first:
  * XML & resource parsing:
    * "port" kXML & xml pull v1 & other free libs used in Android
    * implement AttributeSet
    * implement (get it from Android eclipse plugin) xml files preprocessor which creates R files
    * implement Resource class
  * graphics package:
    * create placeholders for all classes in package
    * implement Canvas
    * implement classes from drawable package
    * implement all others classes
  * Parcel classes
  * text package
  * some classes in os package: Looper, Message, MessageQueue and others

Well. This is quick list and is expected to change.

We need to keep in mind future Android source release. Source release will ease our job, as we will be able to simply copy code. This is why our code is under Apache license. I expect all widgets & layouts to be simply copied in this fashion. So we need to prepare good ground for this.

One more problem thing is float type. We can't drop CLDC1.0 devices, which do not have float type. And Android is making good use of float type. I think we should take preprocessing path: for CLDC1.0 we will preprocess source files and replace float with int, and float operations with MathFP function calls (possible problems with overloaded functions).

>>> See issues for possible work items.