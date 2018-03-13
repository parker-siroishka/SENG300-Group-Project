package astApp;

import org.eclipse.jdt.core.dom.*;


public class ASTCounter extends ASTVisitor{
	
	public int declarationCount = 0;
	public int referenceCount = 0;
	public ASTNode rootNode;
	public String qualTypeToFind;
	
	
	public ASTCounter(ASTNode root, String type){
		
		//super();
		this.rootNode = root;
		this.qualTypeToFind = type;
	}

	public boolean visit(TypeDeclaration node) {
		
		String binding = node.resolveBinding().getQualifiedName();
		if(binding.equals(this.qualTypeToFind)){
			
			this.declarationCount++;
		}
		return true;
	}
	
	public boolean visit(EnumDeclaration node) {
		
		String binding = node.resolveBinding().getQualifiedName();
		if(binding.equals(this.qualTypeToFind)){
			
			this.declarationCount++;
		}
		return true;
	}
	
	public boolean visit(MarkerAnnotation node) {
		
		String binding = node.resolveAnnotationBinding().getName();
		if(binding.equals(this.qualTypeToFind)){
			
			this.declarationCount++;
		}
		return true;
	}
	
	
	public boolean visit(NormalAnnotation node) {
		
		String binding = node.resolveAnnotationBinding().getName();
		if(binding.equals(this.qualTypeToFind)){
			
			this.declarationCount++;
		}
		return true;
	}
	
	
	
	
	
	public int getDeclarationCount() {
		return declarationCount;
	}
	
	public void setDeclarationCount(int declarationCount) {
		this.declarationCount = declarationCount;
	}

	public int getReferenceCount() {
		return referenceCount;
	}

	public void setReferenceCount(int referenceCount) {
		this.referenceCount = referenceCount;
	}

	public ASTNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(ASTNode rootNode) {
		this.rootNode = rootNode;
	}

	public String getQualTypeToFind() {
		return qualTypeToFind;
	}

	public void setQualTypeToFind(String qualTypeToFind) {
		this.qualTypeToFind = qualTypeToFind;
	}
	
	
	
	
	
	
	
	
	
}