/**
 * 
 */
package cn.edu.fudan.se.code.change.ast.visitor;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

import ch.uzh.ifi.seal.changedistiller.model.entities.Delete;
import ch.uzh.ifi.seal.changedistiller.model.entities.Insert;
import ch.uzh.ifi.seal.changedistiller.model.entities.Move;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;
import ch.uzh.ifi.seal.changedistiller.model.entities.Update;
import cn.edu.fudan.se.code.change.tree.bean.CodeRangeList;

/**
 * @author Lotay This is a AST visitor for the tree after change.
 */
public class FileChangedTreeVisitor extends FileTreeVisitor {
	private List<SourceCodeChange> sourceCodeChanges = null;
	private List<SourceCodeChange> filtedValidChanges = null;

	public FileChangedTreeVisitor(String changeRevisionId,String fileName, 
			CodeRangeList codeChangeRangeList,
			List<SourceCodeChange> sourceCodeChanges) {
		super(fileName, changeRevisionId, codeChangeRangeList);
		this.sourceCodeChanges = sourceCodeChanges;
		// System.out.println(this.sourceCodeChanges);
	}

	@Override
	public boolean preVisit2(ASTNode node) {
		int flag = -1;
		if ((flag = this.checkValidNodeLocation(node)) > 0) {
			if (flag == 2) {
				System.out.println(node);
			}
			return true;
		}
		return false;
	}

	private int checkValidNodeLocation(ASTNode node) {
		if (sourceCodeChanges == null || sourceCodeChanges.isEmpty()) {
			return 0;
		}
		int nodeStartIndex = node.getStartPosition();
		int nodeEndIndex = nodeStartIndex + node.getLength();
		for (SourceCodeChange change : sourceCodeChanges) {
			int changeLineStart = -1;
			int changeLineEnd = -1;
			// get the line number
			if (change instanceof Insert) {
				changeLineStart = (change.getChangedEntity()
								.getStartPosition());
				changeLineEnd = (change.getChangedEntity()
								.getEndPosition());
			} else if (change instanceof Move) {
				Move move = (Move) change;
				changeLineStart = (move.getNewEntity().getStartPosition());
				changeLineEnd = (move.getNewEntity().getEndPosition());
			} else if (change instanceof Update) {
				Update update = (Update) change;
				changeLineStart = (update.getNewEntity().getStartPosition());
				changeLineEnd = (update.getNewEntity().getEndPosition());
			} else if (change instanceof Delete) {
				continue;
			}

			/*
			 * Comparing the AST Visitor, The end index of change (from
			 * ChangeDistiller) is less than(1).
			 */
			if (nodeStartIndex >= changeLineStart
					&& nodeEndIndex <= (changeLineEnd + 1)) {
				return 2;
			}
		}
		return 1;
	}

}
