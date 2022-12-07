package com.panjohnny.advent.days;

import com.panjohnny.advent.util.Day;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class Day7 extends Day {
    private final DirectoryTree tree;

    public Day7() {
        super(7);
        this.tree = new DirectoryTree();
    }

    @Override
    public void prepareData() {
        String lastCommand = "";
        for (String line : data.split("\n")) {
            if (line.startsWith("$")) {
                lastCommand = line.substring(1).trim();
                if (lastCommand.startsWith("cd")) {
                    tree.cd(lastCommand.split(" ")[1]);
                }
            } else if (lastCommand.startsWith("ls")) {
                tree.fromString(line.trim());
            }
        }
        tree.toRoot();

        System.out.println(tree);
    }

    private int p1;

    @Override
    public void part1() {
        tree.forEach((node) -> {
            if (node instanceof DirectoryTree.TreeNode.DirectoryNode directoryNode) {
                int s = directoryNode.countSize();
                if (s < 100_000) {
                    p1 += s;
                }
            }
        });

        System.out.printf("Total size of folders < 100k is %d%n", p1);
    }

    private DirectoryTree.TreeNode.DirectoryNode closest = null;
    private int closestDistance = -1;
    @Override
    public void part2() {
        // find the closest directory size to 30M
        final int totalSize = 70_000_000;
        final int desiredSize = 30_000_000 - (totalSize  - tree.size());
        tree.forEach((node) -> {
            if (node instanceof DirectoryTree.TreeNode.DirectoryNode dir) {
                int currentSize = dir.countSize();
                int currentDistance = desiredSize - currentSize;
                if (currentDistance > 0)
                    return;

                if (closestDistance == -1 || closestDistance < currentDistance) {
                    closestDistance = currentDistance;
                    closest = dir;
                }
            }
        });

        System.out.printf("Found " + closest.toString() + " with distance of %d and size of %d%n", closestDistance, closest.countSize());
    }

    private static class DirectoryTree {
        private final TreeNode.DirectoryNode root = new TreeNode.DirectoryNode("/", null);
        private TreeNode currentNode = root;

        public void cd(String path) {
            if (path.equals(root.getName())) {
                currentNode = root;
            } else if (path.equals("..")) {
                up();
            } else if (path.equals("/")) {
                currentNode = root;
            } else {
                currentNode = currentNode.getChildNamed(path);
                if (currentNode == null) {
                    throw new RuntimeException(new FileNotFoundException("File %s not found".formatted(path)));
                }
            }
        }

        public void up() {
            currentNode = currentNode.parent;
            if (currentNode.parent == null) {
                System.err.println("Called up on element with null parent (may be root)");
                currentNode = root;
            }
        }

        public void fromString(String data, String name) {
            if (data.trim().equals("dir")) {
                currentNode.addChild(new TreeNode.DirectoryNode(name, currentNode));
            } else {
                currentNode.addChild(new TreeNode.FileNode(name, currentNode, Integer.parseInt(data.trim())));
            }
        }

        public void fromString(String s) {
            String[] arr = s.split(" ");
            fromString(arr[0], arr[1]);
        }

        public void toRoot() {
            this.currentNode = root;
        }

        public void forEach(Consumer<TreeNode> consumer) {
            consumer.accept(root);
            root.forEach(consumer);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("- ").append(root).append("\n");
            
            root.forEachWithDept((node, depth) -> {
                sb.append("\t".repeat(Math.max(0, depth)));
                sb.append("- ").append(node);
                sb.append("\n");
            }, 1);

            return sb.toString();
        }

        public int size() {
            return root.countSize();
        }

        public static class TreeNode {
            private final String name;
            private final TreeNode parent;
            private final List<TreeNode> children;

            public TreeNode(String name, TreeNode parent) {
                this.name = name;
                this.parent = parent;
                this.children = new ArrayList<>();
            }

            public void addChild(TreeNode child) {
                this.children.add(child);
            }

            public String getName() {
                return name;
            }

            public TreeNode getParent() {
                return parent;
            }

            public List<TreeNode> getChildren() {
                return children;
            }

            public TreeNode getChildNamed(String name) {
                for (TreeNode child : children) {
                    if (child.getName().equals(name)) {
                        return child;
                    }
                }

                throw new RuntimeException(new FileNotFoundException("No child named " + name));
            }

            public void forEach(Consumer<TreeNode> node) {
                for (TreeNode child : children) {
                    node.accept(child);
                    child.forEach(node);
                }
            }

            public void forEachWithDept(BiConsumer<TreeNode, Integer> node, int depth) {
                depth++;
                for (TreeNode child : children) {
                    node.accept(child, depth);
                    child.forEachWithDept(node, depth);
                }
            }

            public static class DirectoryNode extends TreeNode {
                public DirectoryNode(String name, TreeNode parent) {
                    super(name, parent);
                }

                public Set<FileNode> getFiles() {
                    Set<FileNode> files = new HashSet<>();
                    for (TreeNode child : getChildren()) {
                        if (child instanceof FileNode fileNode) {
                            files.add(fileNode);
                        }
                    }

                    return files;
                }

                public Set<DirectoryNode> getDirectories() {
                    Set<DirectoryNode> directories = new HashSet<>();
                    for (TreeNode child : getChildren()) {
                        if (child instanceof DirectoryNode directoryNode) {
                            directories.add(directoryNode);
                        }
                    }

                    return directories;
                }

                public int countSize() {
                    int size = 0;
                    for (TreeNode child : getChildren()) {
                        if (child instanceof FileNode file) {
                            size += file.getSize();
                        } else if (child instanceof DirectoryNode dir) {
                            size += dir.countSize();
                        }
                    }

                    return size;
                }

                @Override
                public String toString() {
                    return getName() + "(dir)";
                }
            }

            public static class FileNode extends TreeNode {
                private final int size;

                public FileNode(String name, TreeNode parent, int size) {
                    super(name, parent);
                    this.size = size;
                }

                public int getSize() {
                    return size;
                }

                @Override
                public String toString() {
                    return getName() + "(file, size=" + size + ")";
                }
            }
        }
    }
}
