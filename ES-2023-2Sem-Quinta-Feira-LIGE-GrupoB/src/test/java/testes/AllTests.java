package testes;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import gestaohorarios.LessonTest;
import gestaohorarios.LessonTimeTest;
import gestaohorarios.TimeTableTest;
import utilities.FileConverterTest;

@RunWith(Suite.class)
@SuiteClasses({ LessonTest.class, LessonTimeTest.class, TimeTableTest.class, FileConverterTest.class })
public class AllTests
{

}
