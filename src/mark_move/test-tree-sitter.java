public class App {
    static {
	System.load("/home/josh/projects/mark-move/resources/native/libjava-tree-sitter.so");
    }

    public static void main(String[] args) {
	System.out.println("Hi");

	System.out.println("A");
	try (Parser parser = new Parser()) {
	    System.out.println("B");
	    parser.setLanguage(Languages.python());
	    try (Tree tree = parser.parseString("def foo(bar, baz):\n  print(bar)\n  print(baz)")) {
		Node root = tree.getRootNode();
		assertEquals(1, root.getChildCount());
		assertEquals("module", root.getType());
		assertEquals(0, root.getStartByte());
		assertEquals(44, root.getEndByte());

		Node function = root.getChild(0);
		assertEquals("function_definition", function.getType());
		assertEquals(5, function.getChildCount());
	    }
	} catch(Exception e) {
	    // Print exception
	    System.out.println(e);
	}
    }
}
